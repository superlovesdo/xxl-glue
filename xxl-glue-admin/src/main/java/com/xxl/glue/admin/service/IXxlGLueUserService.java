package com.xxl.glue.admin.service;


import com.xxl.glue.admin.core.model.User;
import com.xxl.glue.admin.core.result.ReturnT;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by xuxueli on 17/3/30.
 */
public interface IXxlGLueUserService {

    public ReturnT<String> login(HttpServletRequest request, HttpServletResponse response, boolean ifRemember, String userName, String password);

    public ReturnT<String> logout(HttpServletRequest request, HttpServletResponse response);

    public User ifLogin(HttpServletRequest request);

}
