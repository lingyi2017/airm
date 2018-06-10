package com.infosoul.mserver.dto.api;

import java.io.Serializable;

/**
 * 用户信息DTO
 *
 * @author longxy
 * @date 2018-06-09 23:12
 */
public class UserRpDTO implements Serializable {

    private static final long serialVersionUID = 4179596337095551852L;

    /**
     * 姓名或公司名称
     */
    private String name;

    /**
     * 用户类型：0-超级系统管理员；1-管理员；2-普通工作人员；
     */
    private String userType;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 电话
     */
    private String phone;

    /**
     * 昵称
     */
    private String nick;

    /**
     * 头像url
     */
    private String photo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
