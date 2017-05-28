package com.xxl.glue.admin.controller;

import com.xxl.glue.admin.controller.annotation.PermessionLimit;
import com.xxl.glue.admin.core.model.User;
import com.xxl.glue.admin.core.result.ReturnT;
import com.xxl.glue.admin.service.IXxlGLueUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/")
public class IndexController {

	@Resource
	private IXxlGLueUserService xxlGLueUserService;

	@RequestMapping("")
	@PermessionLimit(login=false)
	public String index(Model model, HttpServletRequest request) {
		User loginUser = xxlGLueUserService.ifLogin(request);
		if (loginUser == null) {
			return "redirect:/toLogin";
		}
		return "redirect:/code";
	}

	@RequestMapping("/toLogin")
	@PermessionLimit(login=false)
	public String toLogin(Model model, HttpServletRequest request) {
		User loginUser = xxlGLueUserService.ifLogin(request);
		if (loginUser != null) {
			return "redirect:/";
		}
		return "login";
	}

	@RequestMapping(value="login", method=RequestMethod.POST)
	@ResponseBody
	@PermessionLimit(login=false)
	public ReturnT<String> loginDo(HttpServletRequest request, HttpServletResponse response, String ifRemember, String userName, String password){
		// param
		boolean ifRem = false;
		if (StringUtils.isNotBlank(ifRemember) && "on".equals(ifRemember)) {
			ifRem = true;
		}

		// do login
		ReturnT<String> loginRet = xxlGLueUserService.login(request, response, ifRem, userName, password);
		return loginRet;
	}

	@RequestMapping(value="logout", method=RequestMethod.POST)
	@ResponseBody
	@PermessionLimit(login=false)
	public ReturnT<String> logout(HttpServletRequest request, HttpServletResponse response){
		ReturnT<String> logoutRet = xxlGLueUserService.logout(request, response);
		return logoutRet;
	}

	@RequestMapping("/help")
	@PermessionLimit
	public String index(){
		return "help";
	}
	
}
