package com.xxl.groovy.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xxl.groovy.core.cache.LocalCache;
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
	public Object loadNewInstance(String name) throws Exception{
		if (name==null || name.trim().length()==0) {
			return null;
		}
		Class<?> clazz = loadClass(name);
		if (clazz!=null) {
			Object instance = clazz.newInstance();
			if (instance!=null) {
				this.fillBeanField(instance);
				return instance;
			}
		}
		return null;
	}
	
	// // load instance, singleton
	public static String generateInstanceCacheKey(String name){
		return name+"_instance";
	}
	public Object loadInstance(String name) throws Exception{
		if (name==null || name.trim().length()==0) {
			return null;
		}
		String cacheInstanceKey = generateInstanceCacheKey(name);
		Object cacheClass = LocalCache.getInstance().get(cacheInstanceKey);
		if (cacheClass!=null) {
			return cacheClass;
		}
		Object instance = loadNewInstance(name);
		if (instance!=null) {
			LocalCache.getInstance().set(cacheInstanceKey, instance, cacheTimeout);
			logger.info(">>>>>>>>>>>> xxl-glue, fresh instance, name:{}", name);
			return instance;
		}
		return null;
	}
	
}
