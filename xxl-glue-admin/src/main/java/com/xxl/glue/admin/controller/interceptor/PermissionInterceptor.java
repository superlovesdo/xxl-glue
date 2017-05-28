package com.xxl.glue.admin.controller.interceptor;

import com.xxl.glue.admin.controller.annotation.PermessionLimit;
import com.xxl.glue.admin.core.model.User;
import com.xxl.glue.admin.service.IXxlGLueUserService;
import com.xxl.glue.admin.service.impl.XxlGlueUserServiceImpl;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 权限拦截
 * @author xuxueli 2015-12-12 18:09:04
 */
public class PermissionInterceptor extends HandlerInterceptorAdapter {

	@Resource
	private IXxlGLueUserService xxlGLueUserService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		if (!(handler instanceof HandlerMethod)) {
			return super.preHandle(request, response, handler);
		}

		// if limit
		HandlerMethod method = (HandlerMethod)handler;
		PermessionLimit permission = method.getMethodAnnotation(PermessionLimit.class);
		boolean limit = (permission != null)?permission.login():true;
		boolean superUser = (permission != null)?permission.superUser():false;

		// login user
		User loginUser = xxlGLueUserService.ifLogin(request);
		request.setAttribute(XxlGlueUserServiceImpl.LOGIN_IDENTITY_KEY, loginUser);

		// if pass
		boolean ifPass = false;
		if (limit) {
			if (loginUser == null) {
				ifPass = false;
			} else {
				if (superUser) {
					// 0-普通用户、1-超级管理员
					if (loginUser.getRole() == 1) {
						ifPass = true;
					} else {
						ifPass = false;
					}
				} else {
					ifPass = true;
				}
			}
		} else {
			ifPass = true;
		}

		if (!ifPass) {
			response.sendRedirect("/toLogin");	//request.getRequestDispatcher("/toLogin").forward(request, response);
			return false;
		}

		return super.preHandle(request, response, handler);
	}

}
