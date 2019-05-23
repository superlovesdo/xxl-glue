package com.bk.sv.admin.controller;

import com.bk.sv.admin.controller.annotation.PermissionLimit;
import com.bk.sv.admin.controller.interceptor.PermissionInterceptor;
import com.bk.sv.admin.core.result.ReturnT;
import com.bk.sv.admin.core.util.PropertiesUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/")
public class IndexController {

    @RequestMapping("")
    @PermissionLimit
    public String index(Model model, HttpServletRequest request) {
        return "redirect:/glueinfo";
    }

    @RequestMapping("/toLogin")
    @PermissionLimit(limit = false)
    public String toLogin(Model model, HttpServletRequest request) {
        if (PermissionInterceptor.ifLogin(request)) {
            return "redirect:/";
        }
        return "login";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    @PermissionLimit(limit = false)
    public ReturnT<String> loginDo(HttpServletRequest request, HttpServletResponse response, String userName, String password, String ifRemember) {
        if (!PermissionInterceptor.ifLogin(request)) {
            if (StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(password) && PropertiesUtil.getString("sv.login.username").equals(userName) && PropertiesUtil.getString("sv.login.password").equals(password)) {
                boolean ifRem = false;
                if (StringUtils.isNotBlank(ifRemember) && "on".equals(ifRemember)) {
                    ifRem = true;
                }
                PermissionInterceptor.login(response, ifRem);
            } else {
                return new ReturnT<String>(500, "账号或密码错误");
            }
        }
        return ReturnT.SUCCESS;
    }

    @RequestMapping(value = "logout", method = RequestMethod.POST)
    @ResponseBody
    @PermissionLimit(limit = false)
    public ReturnT<String> logout(HttpServletRequest request, HttpServletResponse response) {
        if (PermissionInterceptor.ifLogin(request)) {
            PermissionInterceptor.logout(request, response);
        }
        return ReturnT.SUCCESS;
    }

    @RequestMapping("/help")
    @PermissionLimit
    public String index() {
        return "help";
    }

}
