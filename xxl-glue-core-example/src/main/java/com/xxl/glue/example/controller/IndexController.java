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
import com.xxl.glue.core.GlueLoader;
import com.xxl.glue.core.handler.GlueHandler;

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
	
	@RequestMapping(value="/code/{name}", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String code(@PathVariable String name) {
		String source = dbGlueLoader.load(name);
		return source;
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
		
		return MessageFormat.format("code name : {0}<hr><pre>{1}</pre>", name, result);
	}
	
	@RequestMapping("/demohandler")
	@ResponseBody
	public Object demo() throws Exception {
		
		Object result = glueFactory.loadInstance("demohandler").handle(null);
		
		return result;
	}
	
}
