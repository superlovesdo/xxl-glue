package com.xxl.admin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xxl.admin.controller.annotation.PermessionLimit;
import com.xxl.admin.controller.interceptor.PermissionInterceptor;
import com.xxl.admin.core.result.ReturnT;

@Controller
@RequestMapping("/")
public class IndexController {
	
	@RequestMapping(value="", method=RequestMethod.GET)
	@PermessionLimit(login=false)
	public String loginPage(HttpServletRequest request){
		if (PermissionInterceptor.ifLogin(request)) {
			return "redirect:/code/";
		}
		return "login";
	}
	
	@RequestMapping("/help")
	@PermessionLimit
	public String index(){
		return "help";
	}
	
	@RequestMapping(value="login", method=RequestMethod.POST)
	@ResponseBody
	@PermessionLimit(login=false)
	public ReturnT<String> loginDo(HttpServletRequest request, HttpServletResponse response, String email, String password){
		if (!PermissionInterceptor.ifLogin(request)) {
			if (StringUtils.isNotBlank(email) && StringUtils.isNotBlank(password)
					&& "admin@qq.com".equals(email) && "123456".equals(password)) {
				PermissionInterceptor.login(response);
			} else {
				return new ReturnT<String>(500, "账号或密码错误");
			}
		}
		return ReturnT.SUCCESS;
	}
	
	@RequestMapping(value="logout", method=RequestMethod.POST)
	@ResponseBody
	@PermessionLimit(login=false)
	public ReturnT<String> logout(HttpServletRequest request, HttpServletResponse response){
		if (PermissionInterceptor.ifLogin(request)) {
			PermissionInterceptor.logout(request, response);
		}
		return ReturnT.SUCCESS;
	}
	
}
