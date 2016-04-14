package com.xxl.groovy.example.service.impl;

import java.util.Map;

import com.xxl.glue.core.handler.GlueHandler;

/**
 * 场景B：托管 “抽象且离散的逻辑单元”
 * 优点：...；逻辑封装（伪服务）；
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
	 * 搜索推荐的商户
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
