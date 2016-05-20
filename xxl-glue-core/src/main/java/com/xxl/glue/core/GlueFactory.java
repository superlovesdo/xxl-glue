package com.xxl.glue.core;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;

import com.xxl.glue.core.cache.LocalCache;
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
	public static String generateInstanceCacheKey(String name){
		return name+"_instance";
	}
	public GlueHandler loadInstance(String name) throws Exception{
		if (name==null || name.trim().length()==0) {
			return null;
		}
		String cacheInstanceKey = generateInstanceCacheKey(name);
		Object cacheInstance = LocalCache.getInstance().get(cacheInstanceKey);
		if (cacheInstance!=null) {
			return (GlueHandler) cacheInstance;
		}
		Object instance = loadNewInstance(name);
		if (instance!=null) {
			LocalCache.getInstance().set(cacheInstanceKey, instance, cacheTimeout);
			logger.info(">>>>>>>>>>>> xxl-glue, fresh instance, name:{}", name);
			return (GlueHandler) instance;
		}
		throw new IllegalArgumentException(">>>>>>>>>>> xxl-glue, loadInstance error, instance is null");
	}
	
	// remove old instance 	(异步刷新缓存，避免缓存雪崩)
	private ExecutorService consumer_threads = Executors.newCachedThreadPool();
	public void freshGlueInstance(final String name){
		if (name==null || name.trim().length()==0) {
			return;
		}
		final String cacheInstanceKey = generateInstanceCacheKey(name);
		consumer_threads.execute(new Runnable() {
			@Override
			public void run() {
				try {
					Object instance = loadNewInstance(name);
					if (instance!=null) {
						LocalCache.getInstance().set(cacheInstanceKey, instance, cacheTimeout);
						logger.info(">>>>>>>>>>>> xxl-glue, async fresh instance, name:{}", name);
					}
				} catch (Exception e) {
					e.printStackTrace();	// 删除时，广播loadNewInstance找不到会报错，后期优化
				}
			}
		});
	}
	
	// ----------------------------- util -----------------------------
	public static Object glue(String name, Map<String, Object> params) throws Exception{
		return GlueFactory.glueFactory.loadInstance(name).handle(params);
	}
	public static void freshGlue(String name){
		GlueFactory.glueFactory.freshGlueInstance(name);
	}
	
}
