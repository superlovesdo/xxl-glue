package com.xxl.glue.core;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;

import com.xxl.glue.core.broadcast.GlueMessage;
import com.xxl.glue.core.broadcast.GlueMessage.GlueMessageType;
import com.xxl.glue.core.handler.GlueHandler;
import com.xxl.glue.core.loader.GlueLoader;

import groovy.lang.GroovyClassLoader;

/**
 * glue factory, product class/object by name
 * @author xuxueli 2016-1-2 20:02:27
 */
public class GlueFactory implements ApplicationContextAware {
	private static Logger logger = LoggerFactory.getLogger(GlueFactory.class);
	
	/**
	 * groovy class loader
	 */
	private GroovyClassLoader groovyClassLoader = new GroovyClassLoader();
	
	/**
	 * glue cache timeout / second
	 */
	private long cacheTimeout = 5000;
	public void setCacheTimeout(long cacheTimeout) {
		this.cacheTimeout = cacheTimeout;
		if (cacheTimeout<-1) {
			cacheTimeout = -1;	// never cache timeout, as -1, until receive message
		}
	}
	
	/**
	 * appName, used to warn-up glue data
	 */
	private String appName;
	public void setAppName(String appName) {
		this.appName = appName;
		if (appName==null || appName.trim().length()==0) {
			this.appName = "default";
		}
	}
	
	/**
	 * code source loader
	 */
	private GlueLoader glueLoader;
	public void setGlueLoader(GlueLoader glueLoader) {
		this.glueLoader = glueLoader;
	}
	
	// ----------------------------- spring support -----------------------------
	private static ApplicationContext applicationContext;
	private static GlueFactory glueFactory;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		GlueFactory.applicationContext = applicationContext;
		GlueFactory.glueFactory = (GlueFactory) applicationContext.getBean("glueFactory");
	}
	
	/**
	 * inject service of spring
	 * @param instance
	 */
	public void injectService(Object instance){
		if (instance==null) {
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
					fieldBean = applicationContext.getBean(field.getName());
				} catch (Exception e) {
				}
				if (fieldBean==null ) {
					fieldBean = applicationContext.getBean(field.getType());
				}
			} else if (AnnotationUtils.getAnnotation(field, Autowired.class) != null) {
				fieldBean = applicationContext.getBean(field.getType());		
			}
			
			if (fieldBean!=null) {
				field.setAccessible(true);
				try {
					field.set(instance, fieldBean);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	// ----------------------------- load instance -----------------------------
	
	// load new instance, prototype
	public GlueHandler loadNewInstance(String name) throws Exception{
		if (name==null || name.trim().length()==0) {
			return null;
		}
		String codeSource = glueLoader.load(name);
		if (codeSource!=null && codeSource.trim().length()>0) {
			Class<?> clazz = groovyClassLoader.parseClass(codeSource);
			if (clazz!=null) {
				Object instance = clazz.newInstance();
				if (instance!=null) {
					if (instance instanceof GlueHandler) {
						this.injectService(instance);
						logger.info(">>>>>>>>>>>> xxl-glue, loadNewInstance success, name:{}", name);
						return (GlueHandler) instance;
					} else {
						throw new IllegalArgumentException(">>>>>>>>>>> xxl-glue, loadNewInstance error, "
								+ "cannot convert from instance["+ instance.getClass() +"] to GlueHandler");
					}
				}
			}
		}
		throw new IllegalArgumentException(">>>>>>>>>>> xxl-glue, loadNewInstance error, instance is null");
	}
	
	// load instance, singleton
	private static final ConcurrentHashMap<String, GlueHandler> glueInstanceMap = new ConcurrentHashMap<String, GlueHandler>();	// cache instance
	private static final ConcurrentHashMap<String, Long> glueCacheMap = new ConcurrentHashMap<String, Long>();					// 
	public GlueHandler loadInstance(String name) throws Exception{
		if (name==null || name.trim().length()==0) {
			return null;
		}
		GlueHandler instance = glueInstanceMap.get(name);
		if (instance == null) {
			instance = loadNewInstance(name);
			if (instance == null) {
				throw new IllegalArgumentException(">>>>>>>>>>> xxl-glue, loadInstance error, instance is null");
			}
			glueInstanceMap.put(name, instance);
			glueCacheMap.put(name, cacheTimeout==-1?-1:(System.currentTimeMillis() + cacheTimeout));
			logger.info(">>>>>>>>>>>> xxl-glue, loadInstance success, name:{}", name);
		} else {
			Long instanceTim = glueCacheMap.get(name);
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
				freshCacheQuene.add(name);
				glueCacheMap.put(name, Long.valueOf(-1));	// 缓存时间临时设置为-1，永久生效，避免并发情况下多次推送异步刷新队列；
			}
		}
		return instance;
	}
	
	// async clear cache (异步刷新缓存，避免缓存雪崩)
	private static LinkedBlockingQueue<String> freshCacheQuene = new LinkedBlockingQueue<String>();
	private static ExecutorService freshCacheWorder = Executors.newCachedThreadPool();
	static{
		freshCacheWorder.execute(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						String name = freshCacheQuene.poll();
						if (name!=null && name.trim().length()>0 && glueInstanceMap.get(name)!=null) {
							GlueHandler instance = GlueFactory.glueFactory.loadNewInstance(name);
							if (instance!=null) {
								glueInstanceMap.put(name, instance);
								glueCacheMap.put(name, GlueFactory.glueFactory.cacheTimeout==-1?-1:(System.currentTimeMillis() + GlueFactory.glueFactory.cacheTimeout));
								logger.info(">>>>>>>>>>>> xxl-glue, async fresh cache by new instace success, name:{}", name);
							}
						} else {
							TimeUnit.SECONDS.sleep(3);
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
	}
	
	// ----------------------------- util -----------------------------
	public static Object glue(String name, Map<String, Object> params) throws Exception{
		return GlueFactory.glueFactory.loadInstance(name).handle(params);
	}
	public static void clearCache(GlueMessage glueMessage){
		// check if match appName
		boolean isMatchAppName = true;
		if (glueMessage.getAppnames()!=null && glueMessage.getAppnames().size()>0) {
			if (glueMessage.getAppnames().contains(GlueFactory.glueFactory.appName)) {
				isMatchAppName = true;
			} else {
				isMatchAppName = false;
			}
		} else {
			isMatchAppName = true;
		}
		logger.info(">>>>>>>>>>> xxl-glue, receive glue message, glue:{}, isMatch:{}", glueMessage.getName(), isMatchAppName);
		
		if (isMatchAppName) {
			if (glueMessage.getType() == GlueMessageType.CLEAR_CACHE) {
				GlueFactory.freshCacheQuene.add(glueMessage.getName());
			} else if (glueMessage.getType() == GlueMessageType.DELETE) {
				glueInstanceMap.remove(glueMessage.getName());
				glueCacheMap.remove(glueMessage.getName());
			}
		}
	}
	
}
