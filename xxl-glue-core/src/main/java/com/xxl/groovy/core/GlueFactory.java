package com.xxl.groovy.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xxl.groovy.core.cache.LocalCache;
import com.xxl.groovy.core.service.GlueHandler;
import com.xxl.groovy.core.support.SpringSupport;

import groovy.lang.GroovyClassLoader;

/**
 * glue factory, product class/object by name
 * @author xuxueli 2016-1-2 20:02:27
 */
public class GlueFactory {
	private static Logger logger = LoggerFactory.getLogger(GlueFactory.class);
	
	// groovy class loader
	private GroovyClassLoader groovyClassLoader = new GroovyClassLoader();
	
	// glue cache timeout / second
	private long cacheTimeout = 5000;
	public void setCacheTimeout(long cacheTimeout) {
		this.cacheTimeout = cacheTimeout;
	}
	
	// code source loader
	private GlueLoader glueLoader;
	public void setGlueLoader(GlueLoader glueLoader) {
		this.glueLoader = glueLoader;
	}
	
	// spring support
	private SpringSupport springSupport;
	public void setSpringSupport(SpringSupport springSupport) {
		this.springSupport = springSupport;
	}
	public void fillBeanField(Object instance){
		if (springSupport!=null) {
			springSupport.fillBeanField(instance);
		}
	}
	
	// load class, 
	public static String generateClassCacheKey(String name){
		return name+"_class";
	}
	public Class<?> loadClass(String name) throws Exception{
		if (name==null || name.trim().length()==0) {
			return null;
		}
		String cacheClassKey = generateClassCacheKey(name);
		Object cacheClass = LocalCache.getInstance().get(cacheClassKey);
		if (cacheClass != null) {
			return (Class<?>) cacheClass;
		}
		String codeSource = glueLoader.load(name);
		if (codeSource!=null && codeSource.trim().length()>0) {
			Class<?> clazz = groovyClassLoader.parseClass(codeSource);
			if (clazz!=null) {
				LocalCache.getInstance().set(cacheClassKey, clazz, cacheTimeout);
				logger.info(">>>>>>>>>>>> xxl-glue, fresh class, name:{}", name);
				return clazz;
			}
		}
		return null;
	}
	
	// load new instance, prototype
	public GlueHandler loadNewInstance(String name) throws Exception{
		if (name==null || name.trim().length()==0) {
			return null;
		}
		Class<?> clazz = loadClass(name);
		if (clazz!=null) {
			Object instance = clazz.newInstance();
			if (instance!=null) {
				if (!(instance instanceof GlueHandler)) {
					throw new IllegalArgumentException(">>>>>>>>>>> xxl-glue, loadNewInstance error, "
							+ "cannot convert from instance["+ instance.getClass() +"] to GlueHandler");
				}
				
				this.fillBeanField(instance);
				return (GlueHandler) instance;
			}
		}
		throw new IllegalArgumentException(">>>>>>>>>>> xxl-glue, loadNewInstance error, instance is null");
	}
	
	// // load instance, singleton
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
			if (!(cacheInstance instanceof GlueHandler)) {
				throw new IllegalArgumentException(">>>>>>>>>>> xxl-glue, loadInstance error, "
						+ "cannot convert from cacheClass["+ cacheInstance.getClass() +"] to GlueHandler");
			}
			return (GlueHandler) cacheInstance;
		}
		Object instance = loadNewInstance(name);
		if (instance!=null) {
			if (!(instance instanceof GlueHandler)) {
				throw new IllegalArgumentException(">>>>>>>>>>> xxl-glue, loadInstance error, "
						+ "cannot convert from instance["+ instance.getClass() +"] to GlueHandler");
			}
			
			LocalCache.getInstance().set(cacheInstanceKey, instance, cacheTimeout);
			logger.info(">>>>>>>>>>>> xxl-glue, fresh instance, name:{}", name);
			return (GlueHandler) instance;
		}
		throw new IllegalArgumentException(">>>>>>>>>>> xxl-glue, loadInstance error, instance is null");
	}
	
}
