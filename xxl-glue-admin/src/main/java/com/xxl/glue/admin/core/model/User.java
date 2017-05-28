package com.xxl.glue.admin.core.model;

/**
 * Created by xuxueli on 17/5/28.
 */
public class User {

    private int id;
    private String userName;   // 用户名
    private String password;   // 密码
    private int role;       // 角色：0-普通用户、1-管理员

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
