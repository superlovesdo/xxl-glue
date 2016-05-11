package com.xxl.glue.example.controller;


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

import com.xxl.glue.core.GlueFactory;
import com.xxl.glue.core.loader.GlueLoader;

/**
 * demo controller
 * @author xuxueli 2016-1-23 16:58:31
 */
@Controller
public class IndexController {
	
	// ---------------------- GLUE源码加载器 测试 -------------------
	@Resource
	private GlueLoader dbGlueLoader;

	@RequestMapping
	@ResponseBody
	public Map<String, Object> index() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tim", System.currentTimeMillis());
		return map;
	}
	
	@RequestMapping(value="/code/{name}", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String code(@PathVariable String name) {
		String source = dbGlueLoader.load(name);
		return source;
	}
	
	// ---------------------- GLUE 测试 -------------------
	
	@RequestMapping("/glue/{name}")
	@ResponseBody
	public String glue(@PathVariable String name) {
		Object result = null;
		
		try {
			result = GlueFactory.glue(name, null);
		} catch (Exception e) {
			Writer writer = new StringWriter();
	        e.printStackTrace(new PrintWriter(writer));
	        result = writer.toString();
		}
		
		return MessageFormat.format("code name : {0}<hr><pre>{1}</pre>", name, result);
	}
	
}
