/**
 * Copyright &copy; 2012-2013 <a href="httparamMap://github.com/free lance/infosys">infosys</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.infosoul.mserver.service.sys;

import java.util.Date;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infosoul.mserver.common.persistence.Page;
import com.infosoul.mserver.common.utils.DateUtils;
import com.infosoul.mserver.common.utils.StringUtils;
import com.infosoul.mserver.entity.sys.SysLog;
import com.infosoul.mserver.repository.hibernate.sys.LogDao;
import com.infosoul.mserver.service.BaseService;



/**
 * 日志Service
 * @author free lance
 * @version 2013-6-2
 */
@Service("sysLogService")
@Transactional(readOnly = true)
public class SysLogService extends BaseService {

	@Autowired
	private LogDao logDao;
	
	public SysLog get(String id) {
		return logDao.get(id);
	}
	
	public Page<SysLog> find(Page<SysLog> page, Map<String, Object> paramMap) {
		DetachedCriteria dc = logDao.createDetachedCriteria();

		String createById = (String) paramMap.get("createById");
		if (StringUtils.isNotBlank(createById)){
			dc.add(Restrictions.eq("createBy.id", createById));
		}
		
		String requestUri = (String) (paramMap.get("requestUri"));
		if (StringUtils.isNotBlank(requestUri)){
			dc.add(Restrictions.like("requestUri", "%"+requestUri+"%"));
		}

		String exception = (String) (paramMap.get("exception"));
		if (StringUtils.isNotBlank(exception)){
			dc.add(Restrictions.eq("type", SysLog.TYPE_EXCEPTION));
		}
		
		Date beginDate = DateUtils.parseDate(paramMap.get("beginDate"));
		if (beginDate == null){
			beginDate = DateUtils.setDays(new Date(), 1);
			paramMap.put("beginDate", DateUtils.formatDate(beginDate, "yyyy-MM-dd"));
		}
		Date endDate = DateUtils.parseDate(paramMap.get("endDate"));
		if (endDate == null){
			endDate = DateUtils.addDays(DateUtils.addMonths(beginDate, 1), -1);
			paramMap.put("endDate", DateUtils.formatDate(endDate, "yyyy-MM-dd"));
		}
		dc.add(Restrictions.between("createDate", beginDate, endDate));
		
		dc.addOrder(Order.desc("id"));
		return logDao.find(page, dc);
	}
	
}
