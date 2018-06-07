/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/free lance/infosys">infosys</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.infosoul.mserver.dto.user;

import java.util.List;

import com.google.common.collect.Lists;
import com.infosoul.mserver.common.persistence.IdEntity;


public class OfficeDTO extends IdEntity<OfficeDTO> {

	private static final long serialVersionUID = 1L;
	private String parentId;	// 父级编号
	private String parentIds; // 所有父级编号
	private String areaId;		// 归属区域
	private String code; 	// 机构编码
	private String name; 	// 机构名称
	private String type; 	// 机构类型（1：公安部；2：公安厅；3：公安局；4：派出所）
	private String grade; 	// 机构等级（1：一级；2：二级；3：三级；4：四级）
	private String address; // 联系地址
	private String zipCode; // 邮政编码
	private String master; 	// 负责人
	private String phone; 	// 电话
	private String fax; 	// 传真
	private String email; 	// 邮箱
	
	private List<UserDTO> userList = Lists.newArrayList();   // 拥有用户列表
	private List<OfficeDTO> childList = Lists.newArrayList();// 拥有子机构列表

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public List<UserDTO> getUserList() {
		return userList;
	}

	public void setUserList(List<UserDTO> userList) {
		this.userList = userList;
	}

	public OfficeDTO(){
		super();
	}
	
	public OfficeDTO(String id){
		this();
		this.id = id;
	}
	

	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getMaster() {
		return master;
	}

	public void setMaster(String master) {
		this.master = master;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<OfficeDTO> getChildList() {
		return childList;
	}

	public void setChildList(List<OfficeDTO> childList) {
		this.childList = childList;
	}


	
	
}