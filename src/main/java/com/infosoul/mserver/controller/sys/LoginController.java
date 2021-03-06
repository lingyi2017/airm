/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/free lance/infosys">infosys</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.infosoul.mserver.controller.sys;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.common.collect.Maps;
import com.infosoul.mserver.common.utils.CacheUtils;
import com.infosoul.mserver.common.utils.CookieUtils;
import com.infosoul.mserver.common.utils.StringUtils;
import com.infosoul.mserver.common.utils.UserUtils;
import com.infosoul.mserver.controller.BaseController;
import com.infosoul.mserver.entity.sys.User;
/**
 * 登录Controller
 * 
 * @author free lance
 * @version 2013-5-31
 */
@Controller
public class LoginController extends BaseController {

    /**
     * 管理登录
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
        User user = UserUtils.getUser();
        // 如果已经登录，则跳转到管理首页
        if (user.getId() != null) {
            return "redirect:/index";
        }
        return "/sys/sysLogin";
    }

    /**
     * 登录失败，真正登录的POST请求由Filter完成
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String username,
            HttpServletRequest request, HttpServletResponse response, Model model) {
        User user = UserUtils.getUser();
        // 如果已经登录，则跳转到管理首页
        if (user.getId() != null) {
            return "redirect:/index";
        }
        model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, username);
        model.addAttribute("isValidateCodeLogin", isValidateCodeLogin(username, true, false));
        return "/sys/sysLogin";
    }

    /**
     * 登录成功，进入管理首页
     */
    @RequiresUser
    @RequestMapping(value = "/index")
    public String index(HttpServletRequest request, HttpServletResponse response) {
        User user = UserUtils.getUser();
        // 未登录，则跳转到登录页
        if (user.getId() == null) {
            return "redirect:/login";
        }
        // 登录成功后，验证码计算器清零
        isValidateCodeLogin(user.getLoginName(), false, true);
        // 登录成功后，获取上次登录的当前站点ID
        UserUtils.putCache("siteId", CookieUtils.getCookie(request, "siteId"));
        return "/sys/sysIndex";
    }
    
    /**
     * 没有权限
     * 
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/unauth")
    public String unauth(Model model) throws Exception {
        if (SecurityUtils.getSubject().isAuthenticated() == false) {
            return "redirect:/login";
        }
        
        return "/unauth";
    }

    /**
     * 获取主题方案
     */
    @RequestMapping(value = "/theme/{theme}")
    public String getThemeInCookie(@PathVariable String theme, HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isNotBlank(theme)) {
            CookieUtils.setCookie(response, "theme", theme);
        } else {
            theme = CookieUtils.getCookie(request, "theme");
        }
        return "redirect:" + request.getParameter("url");
    }

    /**
     * 是否是验证码登录
     * 
     * @param useruame
     *            用户名
     * @param isFail
     *            计数加1
     * @param clean
     *            计数清零
     * @return
     */
    @SuppressWarnings("unchecked")
    public static boolean isValidateCodeLogin(String useruame, boolean isFail, boolean clean) {
        Map<String, Integer> loginFailMap = (Map<String, Integer>) CacheUtils.get("loginFailMap");
        if (loginFailMap == null) {
            loginFailMap = Maps.newHashMap();
            CacheUtils.put("loginFailMap", loginFailMap);
        }
        Integer loginFailNum = loginFailMap.get(useruame);
        if (loginFailNum == null) {
            loginFailNum = 0;
        }
        if (isFail) {
            loginFailNum++;
            loginFailMap.put(useruame, loginFailNum);
        }
        if (clean) {
            loginFailMap.remove(useruame);
        }
        // return loginFailNum >= 3;
        return false;
    }

    @RequestMapping("/download")
    public String download(@RequestParam String filePath, HttpServletResponse response) {
        File file = new File(filePath);
        InputStream inputStream;
        try {
            inputStream = new FileInputStream(filePath);
            response.reset();
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            byte data[] = new byte[1024];
            while (inputStream.read(data, 0, 1024) >= 0) {
                outputStream.write(data);
            }
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
            //e.printStackTrace();
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            //e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return null;
    }
}
