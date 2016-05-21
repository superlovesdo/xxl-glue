//package com.xxl.glue.core.cache;
//
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.TimeUnit;
//
///**
// * local interface
// * @author Administrator
// */
//public class LocalCache implements ICache{
//	
//	private static final LocalCache instance = new LocalCache();
//	public static LocalCache getInstance(){
//		return instance;
//	} 
//	
//	private static final ConcurrentHashMap<String, Object> cacheMap = new ConcurrentHashMap<String, Object>();
//	private static final long CACHE_TIMEOUT = 5000;
//	
//	private static String makeTimKey(String key){
//		return key.concat("_tim");
//	}
//	private static String makeDataKey(String key){
//		return key.concat("_data");
//	}
//	
//	@Override
//	public boolean set(String key, Object value) {
//		cacheMap.put(makeTimKey(key), System.currentTimeMillis() + CACHE_TIMEOUT);
//		cacheMap.put(makeDataKey(key), value);
//		return true;
//	}
//	
//	@Override
//	public boolean set(String key, Object value, long timeout) {
//		if (timeout == -1) {
//			cacheMap.put(makeTimKey(key), -1);	// 永久存储
//		} else {
//			cacheMap.put(makeTimKey(key), System.currentTimeMillis() + timeout);
//		}
//		cacheMap.put(makeDataKey(key), value);
//		return true;
//	}
//
//	@Override
//	public Object get(String key) {
//		Object tim = cacheMap.get(makeTimKey(key));
//		if (tim != null) {
//			long timeoutTim = Long.parseLong(tim.toString());
//			if (timeoutTim == -1) {
//				return cacheMap.get(makeDataKey(key));	// 永久存储
//			} else {
//				if (System.currentTimeMillis() < timeoutTim) {
//					return cacheMap.get(makeDataKey(key));
//				} else {
//					this.remove(key);
//				}
//			}
//		}
//		return null;
//	}
//
//	@Override
//	public boolean remove(String key) {
//		cacheMap.remove(makeTimKey(key));
//		cacheMap.remove(makeDataKey(key));
//		return true;
//	}
//	
//	public static void main(String[] args) {
//		String key = "key01";
//		System.out.println(LocalCache.getInstance().get(key));
//		
//		LocalCache.getInstance().set(key, "v1");
//		System.out.println(LocalCache.getInstance().get(key));
//		
//		LocalCache.getInstance().set(key, "v2");
//		System.out.println(LocalCache.getInstance().get(key));
//		
//		//LocalCache.getInstance().set(key, "v3", 1);
//		LocalCache.getInstance().set(key, "v3", -1);
//		try {
//			TimeUnit.SECONDS.sleep(3);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		System.out.println(LocalCache.getInstance().get(key));
//		
//		LocalCache.getInstance().remove(key);
//		System.out.println(LocalCache.getInstance().get(key));
//		
//	}
//	
//}
