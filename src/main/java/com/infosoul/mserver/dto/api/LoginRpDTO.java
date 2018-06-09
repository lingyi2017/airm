package com.infosoul.mserver.dto.api;

import java.io.Serializable;

/**
 * 登录返回DTO
 *
 * @author longxy
 * @date 2018-06-09 16:50
 */
public class LoginRpDTO implements Serializable {

    private static final long serialVersionUID = 8056137562373696953L;

    /**
     * 令牌
     */
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
