package com.bk.sv.core;

import com.bk.sv.core.broadcast.BkGlueBroadcaster;
import com.bk.sv.core.broadcast.SvMessage;
import com.bk.sv.core.handler.SvHandler;
import com.bk.sv.core.loader.BkLoader;
import com.bk.sv.core.loader.impl.FileBkLoader;
import com.bk.sv.core.loader.util.BkCompiler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * glue factory, product class/object by name
 *
 * @author BK 2016-1-2 20:02:27
 */
public class SvFactory implements ApplicationContextAware {
    // load instance, singleton
    private static final ConcurrentHashMap<String, SvHandler> glueInstanceMap = new ConcurrentHashMap<String, SvHandler>();    // cache instance
    private BkCompiler compiler = new BkCompiler();
    private static final ConcurrentHashMap<String, Long> timeoutMap = new ConcurrentHashMap<String, Long>();                    // cache timeout tim
    private static Logger logger = LoggerFactory.getLogger(SvFactory.class);
    private static ApplicationContext applicationContext;
    private static SvFactory svFactory;
    private static LinkedBlockingQueue<SvMessage> refreshQueue = new LinkedBlockingQueue<SvMessage>();    // 异步刷新 + version校验：避免缓存雪崩
    private static ConcurrentHashMap<String, Long> glueVersionMap = new ConcurrentHashMap<String, Long>();
    private long cacheTimeout = 5000;                                        // glue cache timeout / second
    private String appName;    // appName, used to warn-up glue data
    private BkLoader bkLoader = new FileBkLoader();                    // code source loader
    private Thread refreshThread = null;

    // ----------------------------- load instance -----------------------------
    private boolean refreshToStop = false;

    public static void refresh(SvMessage svMessage) {
        // check if match appName
        boolean isMatchAppName = true;
        if (svMessage.getAppnames() != null && svMessage.getAppnames().size() > 0) {
            if (svMessage.getAppnames().contains(SvFactory.svFactory.appName)) {
                isMatchAppName = true;
            } else {
                isMatchAppName = false;
            }
        } else {
            isMatchAppName = true;
        }
        if (!isMatchAppName) {
            return;
        }

        // fresh instance
        SvFactory.refreshQueue.add(svMessage);
    }

    // ----------------------------- util -----------------------------
    public static Object glue(String name, Map<String, Object> params) throws Exception {
        SvHandler instance = svFactory.loadInstance(name);
        System.out.println(11212);
        return instance.handler(params);
    }

    public void setCacheTimeout(long cacheTimeout) {
        this.cacheTimeout = cacheTimeout;
        if (cacheTimeout < -1) {
            cacheTimeout = -1;    // never cache timeout, as -1, until receive message
        }
    }

    // ----------------------------- async glue refresh -----------------------------

    public void setAppName(String appName) {
        this.appName = appName;
        if (appName == null || appName.trim().length() == 0) {
            this.appName = "default";
        }
    }

    public void setBkLoader(BkLoader bkLoader) {
        this.bkLoader = bkLoader;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SvFactory.applicationContext = applicationContext;
        SvFactory.svFactory = (SvFactory) applicationContext.getBean("svFactory");
    }

    /**
     * inject service of spring
     *
     * @param instance
     * @throws Exception
     */
    public void injectService(Object instance) throws Exception {
        if (instance == null) {
            return;
        }

        Field[] fields = instance.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }

            Object fieldBean = null;
            // with bean-id, bean could be found by both @Resource and @Autowired, or bean could only be found by @Autowired
            if (AnnotationUtils.getAnnotation(field, Resource.class) != null) {
                try {
                    Resource resource = AnnotationUtils.getAnnotation(field, Resource.class);
                    if (resource.name() != null && resource.name().length() > 0) {
                        fieldBean = applicationContext.getBean(resource.name());
                    } else {
                        fieldBean = applicationContext.getBean(field.getName());
                    }
                } catch (Exception e) {
                }
                if (fieldBean == null) {
                    fieldBean = applicationContext.getBean(field.getType());
                }
            } else if (AnnotationUtils.getAnnotation(field, Autowired.class) != null) {
                Qualifier qualifier = AnnotationUtils.getAnnotation(field, Qualifier.class);
                if (qualifier != null && qualifier.value() != null && qualifier.value().length() > 0) {
                    fieldBean = applicationContext.getBean(qualifier.value());
                } else {
                    fieldBean = applicationContext.getBean(field.getType());
                }
            }

            if (fieldBean != null) {
                field.setAccessible(true);
                field.set(instance, fieldBean);
            }
        }
    }

    // load new instance, prototype
    public SvHandler loadNewInstance(String name) throws Exception {
        if (name == null || name.trim().length() == 0) {
            return null;
        }
        String codeSource = bkLoader.load(name);
        if (codeSource != null && codeSource.trim().length() > 0) {
            Class<?> clazz = compiler.compile("Script1", codeSource, null, null, null);
            if (clazz != null) {
                Object instance = clazz.newInstance();
                if (instance != null) {
                    if (instance instanceof SvHandler) {
                        this.injectService(instance);
                        logger.info(">>>>>>>>>>>> loadNewInstance success, name:{}", name);
                        // watch topic on zk
                        BkGlueBroadcaster.getInstance().watchMsg(name);
                        return (SvHandler) instance;
                    } else {
                        throw new IllegalArgumentException(">>>>>>>>>>> sv, loadNewInstance error, " + "cannot convert from instance[" + instance.getClass() + "] to GlueHandler");
                    }
                }
            }

        }
        throw new IllegalArgumentException(">>>>>>>>>>> sv, loadNewInstance error, instance is null");
    }

    public SvHandler loadInstance(String name) throws Exception {
        if (name == null || name.trim().length() == 0) {
            return null;
        }
        SvHandler instance = glueInstanceMap.get(name);
        if (instance == null) {
            instance = loadNewInstance(name);
            if (instance == null) {
                throw new IllegalArgumentException(">>>>>>>>>>> sv, loadInstance error, instance is null");
            }
            glueInstanceMap.put(name, instance);
            timeoutMap.put(name, cacheTimeout == -1 ? -1 : (System.currentTimeMillis() + cacheTimeout));
        } else {
            Long instanceTim = timeoutMap.get(name);
            boolean ifValid = true;
            if (instanceTim == null) {
                ifValid = false;
            } else {
                if (instanceTim.intValue() == -1) {
                    ifValid = true;
                } else if (System.currentTimeMillis() > instanceTim) {
                    ifValid = false;
                }
            }
            if (!ifValid) {
                SvMessage svMessage = new SvMessage();
                svMessage.setGlueName(name);
                refreshQueue.add(svMessage);

                timeoutMap.put(name, Long.valueOf(-1));    // 缓存时间临时设置为-1，永久生效，避免并发情况下多次推送异步刷新队列；
            }
        }

        return instance;
    }

    private void init() {
        refreshThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!refreshToStop) {
                    SvMessage svMessage = null;
                    try {
                        svMessage = refreshQueue.take();    // take glue need refresh
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                        continue;
                    }

                    // refresh
                    if (svMessage != null && svMessage.getGlueName() != null && glueInstanceMap.get(svMessage.getGlueName()) != null) {

                        // instance version
                        Long existVersion = glueVersionMap.get(glueVersionMap);
                        if (existVersion != null && svMessage.getVersion() > 0 && existVersion.longValue() == svMessage.getVersion()) {
                            continue;
                        }

                        // refresh new instance
                        SvHandler newInstance = null;
                        try {
                            newInstance = SvFactory.svFactory.loadNewInstance(svMessage.getGlueName());
                        } catch (Exception e) {
                            logger.error("", e);
                        }

                        if (newInstance != null) {
                            glueInstanceMap.put(svMessage.getGlueName(), newInstance);
                            timeoutMap.put(svMessage.getGlueName(), cacheTimeout == -1 ? -1 : (System.currentTimeMillis() + cacheTimeout));

                            logger.warn(">>>>>>>>>>>> sv, async glue fresh success, name:{}", svMessage.getGlueName());
                        } else {
                            glueInstanceMap.remove(svMessage);
                            timeoutMap.remove(svMessage);

                            logger.warn(">>>>>>>>>>>> sv, async glue fresh fail, old instance removed, name:{}", svMessage.getGlueName());
                        }
                    }
                }
            }
        });
        refreshThread.setDaemon(true);
        refreshThread.start();
    }

    private void destory() {
        refreshToStop = true;
    }

    public static void main(String[] args) throws Exception {
        SvFactory svFactory = new SvFactory();
        svFactory.setBkLoader(new BkLoader() {
            @Override
            public String load(String name) {
                return "package com.bk.groovy.core;" + "import com.sv.core.handler.GlueHandler;" + "public class DemoImpl implements GlueHandler {" + "public Object handle(Map<String, Object> params) {" + "return 999;" + "}" + "}" + ";";
            }
        });

        SvHandler service = svFactory.loadNewInstance("ssss");
        System.out.println(service);
        System.out.println(service.handler(null));
    }
}
