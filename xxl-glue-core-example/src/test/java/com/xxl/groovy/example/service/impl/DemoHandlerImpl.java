package com.xxl.groovy.example.service.impl;

import java.util.Map;

import com.xxl.groovy.core.service.GlueHandler;


public class DemoHandlerImpl implements GlueHandler {

	@Override
	public Object handle(Map<String, Object> params) {
		return "666";
	}
	
}
