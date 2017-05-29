package com.xxl.glue.example.controller;


import com.xxl.glue.core.GlueFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.MessageFormat;

/**
 * demo controller
 * @author xuxueli 2016-1-23 16:58:31
 */
@Controller
public class IndexController {
	
	@RequestMapping("")
	@ResponseBody
	public String index(String name) {
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
