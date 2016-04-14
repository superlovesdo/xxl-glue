package com.xxl.groovy.example.service.impl;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.xxl.glue.core.handler.GlueHandler;

/**
 * 场景B：托管 “静态方法”，
 * 优点：...； + 组件共享；
 * @author xuxueli 2016-4-14 16:07:03
 */
public class DemoHandlerBImpl implements GlueHandler {
	private static final String SHOPID = "shopid";
	
	private static Set<Integer> blackShops = new HashSet<Integer>();
	static {
		blackShops.add(15826714);
		blackShops.add(15826715);
		blackShops.add(15826716);
		blackShops.add(15826717);
		blackShops.add(15826718);
		blackShops.add(15826719);
	}
	
	/**
	 * 商户黑名单判断
	 */
	@Override
	public Object handle(Map<String, Object> params) {
		int shopid = 0;
		if (params!=null && params.get(SHOPID)!=null) {
			shopid = (Integer) params.get(SHOPID);
		}
		if (shopid > 0 && blackShops.contains(shopid)) {
			return true;
		}
		return false;
	}
	
}
