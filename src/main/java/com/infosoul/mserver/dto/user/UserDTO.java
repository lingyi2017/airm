/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/free lance/infosys">infosys</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.infosoul.mserver.dto.user;

import java.util.Date;
import java.util.List;


public class UserDTO  {

	private String id;
	
	private String officeId;// 登录名
	
	private String companyId;// 登录名
	
	private String loginName;// 登录名
	
	private String password;// 密码
	
	private String no; // 工号
	
	private String name; // 姓名或公司名称
	
	private String nick;
	
	private Date birthday; // 生日
	
	private String address;
	
	private String postalcode; // 邮编
	
	private String email; // 邮箱
	
	private String phone; // 电话
	
	private String mobile; // 手机
	
	private String userType;// 用户类型
	
	private String loginIp; // 最后登陆IP
	
	// retailer 中的信息
	private Double lat;  // 经度
	
    private Double lon;  // 纬度
	
	private List<RoleDTO> roleList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostalcode() {
		return postalcode;
	}

	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public List<RoleDTO> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<RoleDTO> roleList) {
		this.roleList = roleList;
	}
	
	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLon() {
		return lon;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}

	@Override
	public String toString() {
		return "UserDTO [id=" + id + ", officeId=" + officeId + ", companyId="
				+ companyId + ", loginName=" + loginName + ", password="
				+ password + ", no=" + no + ", name=" + name + ", nick=" + nick
				+ ", birthday=" + birthday + ", address=" + address
				+ ", postalcode=" + postalcode + ", email=" + email
				+ ", phone=" + phone + ", mobile=" + mobile + ", userType="
				+ userType + ", loginIp=" + loginIp + "]";
	}
	
	
}