/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/free lance/infosys">infosys</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.infosoul.mserver.interceptor;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.infosoul.mserver.common.utils.ApplicationContextHelper;
import com.infosoul.mserver.common.utils.StringUtils;
import com.infosoul.mserver.common.utils.UserUtils;
import com.infosoul.mserver.entity.sys.SysLog;
import com.infosoul.mserver.entity.sys.User;
import com.infosoul.mserver.repository.hibernate.sys.LogDao;
import com.infosoul.mserver.service.BaseService;

/**
 * 系统拦截器
 * 
 * @author free lance
 * @version 2013-6-6
 */
public class LogInterceptor extends BaseService implements HandlerInterceptor {

    private static LogDao logDao = ApplicationContextHelper.getBean(LogDao.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            String viewName = modelAndView.getViewName();
            // UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
            // if(viewName.startsWith("modules/") &&
            // DeviceType.MOBILE.equals(userAgent.getOperatingSystem().getDeviceType())){
            // modelAndView.setViewName(viewName.replaceFirst("modules", "mobile"));
            // }
        }
    }

    @Override
    @Transactional
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

        String requestRri = request.getRequestURI();
        String uriPrefix = request.getContextPath();
        String requestMethod = request.getMethod();

        if ((StringUtils.startsWith(requestRri, uriPrefix) && (StringUtils.containsIgnoreCase(requestRri, "save")
                || StringUtils.containsIgnoreCase(requestRri, "delete") || StringUtils.containsIgnoreCase(requestRri, "import") 
                || StringUtils.containsIgnoreCase(requestRri, "update"))) || ex != null || !requestMethod.equals("GET")) {

            User user = UserUtils.getUser();
            if (user != null && user.getId() != null) {

                StringBuilder params = new StringBuilder();
                int index = 0;
                for (Object param : request.getParameterMap().keySet()) {
                    params.append((index++ == 0 ? "" : "&") + param + "=");
                    params.append(StringUtils.abbr(StringUtils.endsWithIgnoreCase((String) param, "password") ? ""
                            : request.getParameter((String) param), 100));
                }

                SysLog log = new SysLog();
                log.setType(ex == null ? SysLog.TYPE_ACCESS : SysLog.TYPE_EXCEPTION);
                log.setCreateBy(user);
                log.setCreateDate(new Date());
                log.setRemoteAddr(StringUtils.getRemoteAddr(request));
                log.setUserAgent(request.getHeader("user-agent"));
                log.setRequestUri(request.getRequestURI());
                log.setMethod(request.getMethod());
                log.setParams(params.toString());
                log.setException(ex != null ? ex.toString() : "");
                logDao.save(log);

                logger.info("save log {type: {}, loginName: {}, uri: {}}, ", log.getType(), user.getLoginName(),
                        log.getRequestUri());

            }
        }

        // logger.debug("最大内存: {}, 已分配内存: {}, 已分配内存中的剩余空间: {}, 最大可用内存: {}",
        // Runtime.getRuntime().maxMemory(), Runtime.getRuntime().totalMemory(), Runtime.getRuntime().freeMemory(),
        // Runtime.getRuntime().maxMemory()-Runtime.getRuntime().totalMemory()+Runtime.getRuntime().freeMemory());

    }

}
