package com.xxl.glue.admin.service.impl;


import com.xxl.glue.admin.core.model.User;
import com.xxl.glue.admin.core.result.ReturnT;
import com.xxl.glue.admin.core.util.CookieUtil;
import com.xxl.glue.admin.dao.IUesrDao;
import com.xxl.glue.admin.service.IXxlGLueUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;

/**
 * Created by xuxueli on 17/3/30.
 */
@Service
public class XxlGlueUserServiceImpl implements IXxlGLueUserService {

    public static final String LOGIN_IDENTITY_KEY = "XXL_API_LOGIN_IDENTITY";
    private static String makeToken (User xxlApiUser) {
        String tokenStr = xxlApiUser.getUserName() + "_" + xxlApiUser.getPassword() + "_" + xxlApiUser.getRole();
        String token = new BigInteger(1, tokenStr.getBytes()).toString(16);
        return token;
    }
    private static User parseToken(HttpServletRequest request){
        String token = CookieUtil.getValue(request, LOGIN_IDENTITY_KEY);
        if (token != null) {
            String tokenStr = new String(new BigInteger(token, 16).toByteArray());
            String[] tokenArr = tokenStr.split("_");
            if (tokenArr!=null && tokenArr.length==3) {
                User user = new User();
                user.setUserName(tokenArr[0]);
                user.setPassword(tokenArr[1]);
                user.setRole(Integer.valueOf(tokenArr[2]));
                return user;
            }
        }

        return null;
    }

    @Resource
    private IUesrDao userDao;

    @Override
    public ReturnT<String> login(HttpServletRequest request, HttpServletResponse response, boolean ifRemember, String userName, String password) {

        User loginUser = ifLogin(request);
        if (loginUser != null) {
            return ReturnT.SUCCESS;
        }

        User user = userDao.findByUserName(userName);
        if (user == null) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, "登录用户不存在");
        }
        if (!user.getPassword().equals(password)) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, "登录密码错误");
        }

        String token = makeToken(user);
        CookieUtil.set(response, LOGIN_IDENTITY_KEY, token, ifRemember);
        return ReturnT.SUCCESS;
    }

    @Override
    public ReturnT<String> logout(HttpServletRequest request, HttpServletResponse response) {

        User loginUser = ifLogin(request);
        if (loginUser == null) {
            return ReturnT.SUCCESS;

        }

        CookieUtil.remove(request, response, LOGIN_IDENTITY_KEY);
        return ReturnT.SUCCESS;
    }

    @Override
    public User ifLogin(HttpServletRequest request) {
        User loginUser = parseToken(request);
        return loginUser;
    }
}
