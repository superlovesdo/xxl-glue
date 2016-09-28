package com.xxl.groovy.example.service.impl;

import com.xxl.glue.core.handler.GlueHandler;

import java.util.Map;

/**
 * 场景B：托管 “动态服务”
 * 优点：可以灵活组装接口和服务, 扩展服务的动态特性，作为公共模块。
 * @author xuxueli 2016-4-14 16:07:03
 */
public class DemoHandlerCImpl implements GlueHandler {
	private static final String SHOPID = "shopid";
	
	/*
	@Resource
	private AccountService accountService;
	@Autowired
	private AccountService accountServiceB;
	*/
	
	/**
	 * 商户黑名单判断
	 */
	@Override
	public Object handle(Map<String, Object> params) {
		
		/*
		int shopid = 0;
		if (params!=null && params.get(SHOPID)!=null) {
			shopid = (Integer) params.get(SHOPID);
		}
		if (shopid > 0 && accountService.isBlackShop()) {
			return true;
		}
		*/
		
		return false;
	}
	
}
