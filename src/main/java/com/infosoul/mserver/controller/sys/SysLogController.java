/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/free lance/infosys">infosys</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.infosoul.mserver.controller.sys;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.infosoul.mserver.common.persistence.Page;
import com.infosoul.mserver.controller.BaseController;
import com.infosoul.mserver.entity.sys.SysLog;
import com.infosoul.mserver.service.sys.SysLogService;

/**
 * 日志Controller
 * @author free lance
 * @version 2013-6-2
 */
@Controller
@RequestMapping(value = "/sys/log")
public class SysLogController extends BaseController {

	@Autowired
	private SysLogService logService;
	
	@RequiresPermissions("sys:log:view")
	@RequestMapping(value = {"list", ""})
	public String list(@RequestParam Map<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<SysLog> page = logService.find(new Page<SysLog>(request, response), paramMap); 
        model.addAttribute("page", page);
        model.addAllAttributes(paramMap);
		return "/sys/logList";
	}

}
