package com.infosoul.mserver.dto.api;

import java.io.Serializable;

/**
 * 登录请求DTO
 *
 * @author longxy
 * @date 2018-06-09 15:42
 */
public class LoginRqDTO implements Serializable {

    private static final long serialVersionUID = -9175282359250284805L;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

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
}
