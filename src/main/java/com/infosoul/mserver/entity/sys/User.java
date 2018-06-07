/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/free lance/infosys">infosys</a> All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.infosoul.mserver.entity.sys;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.infosoul.mserver.common.persistence.IdEntity;
import com.infosoul.mserver.common.utils.Collections3;
import com.infosoul.mserver.common.utils.excel.annotation.ExcelField;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.*;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 用户Entity
 *
 * @author free lance
 * @version 2013-5-15
 */
@Entity
@Table(name = "sys_user")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class User extends IdEntity<User> {

    private static final long serialVersionUID = 1L;
    private Office company; // 归属机构
    private Office office; // 归属部门
    private String loginName;// 登录名
    private String password;// 密码
    private String no; // 工号
    private String name; // 姓名或公司名称
    private Date birthday; // 生日
    private String email; // 邮箱
    private String postalcode; // 邮编
    private String phone; // 电话
    private String mobile; // 手机
    private String userType;//用户类型：0-超级系统管理员；1-运营人员；2-零售商户；3-消费者
    private String loginIp; // 最后登陆IP
    private Date loginDate; // 最后登陆日期
    private Date activeDate; // 激活日期

    // 补充字段
    private String qq;
    private String weixin;
    private String nick;
    private String photo; // 头像url
    private String sex;
    private String address;


    private List<Role> roleList = Lists.newArrayList(); // 拥有角色列表

    @PrePersist
    public void prePersist() {
        super.prePersist();
        this.activeDate = this.createDate;
    }

    public User() {
        super();
    }

    public User(String id) {
        this();
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "company_id")
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonIgnore
    @NotNull(message = "归属机构不能为空")
    //@ExcelField(title = "归属机构", align = 2, sort = 20)
    public Office getCompany() {
        return company;
    }

    public void setCompany(Office company) {
        this.company = company;
    }

    @ManyToOne
    @JoinColumn(name = "office_id")
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonIgnore
    @NotNull(message = "归属部门不能为空")
    //@ExcelField(title = "归属部门", align = 2, sort = 25)
    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    //@ExcelField(title = "登录名", align = 2, sort = 30)
    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    @JsonIgnore
    @Length(min = 1, max = 100)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //@Length(min = 1, max = 100)
    @ExcelField(title = "公司名称", align = 2, sort = 10)
    public String getName() {
        return name;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Length(min = 1, max = 100)
    //@ExcelField(title = "工号", align = 2, sort = 45)
    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Email
    @Length(min = 0, max = 200)
    //@ExcelField(title = "邮箱", align = 1, sort = 50)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @ExcelField(title = "电话", align = 2, sort = 30)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Length(min = 0, max = 200)
    //@ExcelField(title = "手机", align = 2, sort = 70)
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Transient
    //@ExcelField(title = "备注", align = 1, sort = 900)
    public String getRemarks() {
        return remarks;
    }

    @Length(min = 0, max = 100)
    //@ExcelField(title = "用户类型", align = 2, sort = 80, dictType = "sys_user_type")
    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Transient
    //@ExcelField(title = "创建时间", type = 0, align = 1, sort = 90)
    public Date getCreateDate() {
        return createDate;
    }

    //@ExcelField(title = "最后登录IP", type = 1, align = 1, sort = 100)
    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //@ExcelField(title = "最后登录日期", type = 1, align = 1, sort = 110)
    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "sys_user_role", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    @Where(clause = "del_flag='" + DEL_FLAG_NORMAL + "'")
    @OrderBy("id")
    @Fetch(FetchMode.SUBSELECT)
    @NotFound(action = NotFoundAction.IGNORE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    //@ExcelField(title = "拥有角色", align = 1, sort = 800, fieldType = RoleListType.class)
    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    @Transient
    @JsonIgnore
    public List<String> getRoleIdList() {
        List<String> roleIdList = Lists.newArrayList();
        for (Role role : roleList) {
            roleIdList.add(role.getId());
        }
        return roleIdList;
    }

    @Transient
    public void setRoleIdList(List<String> roleIdList) {
        roleList = Lists.newArrayList();
        for (String roleId : roleIdList) {
            Role role = new Role();
            role.setId(roleId);
            roleList.add(role);
        }
    }

    /**
     * 用户拥有的角色名称字符串, 多个角色名称用','分隔.
     */
    @Transient
    public String getRoleNames() {
        return Collections3.extractToString(roleList, "name", ", ");
    }

    @Transient
    @JsonIgnore
    public String getRoleListNames() {
        return Collections3.extractToString(roleList, "id", ", ");
    }

    @Transient
    public boolean isAdmin() {
        return isAdmin(this.id);
    }

    @Transient
    public static boolean isAdmin(String id) {
        return id != null && id.equals("admin");
    }

    @Column(name = "qq", length = 100)
    //@ExcelField(title = "QQ", align = 2, sort = 120)
    public String getQq() {
        return this.qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    @Column(name = "weixin", length = 100)
    //@ExcelField(title = "微信", align = 2, sort = 125)
    public String getWeixin() {
        return this.weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    @Column(name = "nick", length = 100)
    //@ExcelField(title = "昵称", align = 2, sort = 130)
    public String getNick() {
        return this.nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    @Column(name = "photo", length = 100)
    public String getPhoto() {
        return this.photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Column(name = "sex", length = 1)
    //@ExcelField(title = "性别", align = 2, sort = 135)
    public String getSex() {
        return this.sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Column(name = "address", length = 255)
    @ExcelField(title = "地址", align = 2, sort = 40)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "active_date")
    public Date getActiveDate() {
        return activeDate;
    }

    public void setActiveDate(Date activeDate) {
        this.activeDate = activeDate;
    }

    @Column(name = "postalcode")
    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

}