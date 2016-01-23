package com.xxl.groovy.example.controller;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xxl.groovy.core.GlueFactory;
import com.xxl.groovy.core.GlueLoader;
import com.xxl.groovy.core.service.GlueHandler;

/**
 * demo controller
 * @author xuxueli 2016-1-23 16:58:31
 */
@Controller
public class IndexController {
	
	@Resource
	private GlueLoader dbGlueLoader;
	@Resource
	private GlueFactory glueFactory;

	@RequestMapping
	@ResponseBody
	public Map<String, Object> index() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tim", System.currentTimeMillis());
		return map;
	}
	
	@RequestMapping("/code/{name}")
	@ResponseBody
	public String code(@PathVariable String name) {
		String source = dbGlueLoader.load(name);
		return MessageFormat.format("code name : {0}<hr>{1}", name, source);
	}
	
	@RequestMapping("/glue/{name}")
	@ResponseBody
	public String glue(@PathVariable String name) {
		Object result = null;
		
		try {
			GlueHandler handler = (GlueHandler) glueFactory.loadInstance(name);
			if (handler!=null) {
				result = handler.handle(null);
			}
		} catch (Exception e) {
			Writer writer = new StringWriter();
	        e.printStackTrace(new PrintWriter(writer));
	        result = writer.toString();
		}
		
		return MessageFormat.format("code name : {0}<hr>{1}", name, result);
	}
	
}
