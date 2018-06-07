package com.infosoul.mserver.api.sys;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.infosoul.mserver.api.Result;
import com.infosoul.mserver.common.mapper.JsonMapper;
import com.infosoul.mserver.common.security.UsernamePasswordToken;
import com.infosoul.mserver.common.utils.Constant;
import com.infosoul.mserver.common.utils.UserUtils;
import com.infosoul.mserver.common.web.MediaTypes;
import com.infosoul.mserver.entity.sys.User;
import com.infosoul.mserver.service.sys.SystemService;
import com.infosoul.mserver.webservice.rest.util.AppResultUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * 登录RESTFull接口
 *
 * @author longxy
 * @date 2018-06-07 22:43
 */
@Path("/user")
@Consumes(MediaTypes.JSON_UTF_8)
@Produces(MediaTypes.JSON_UTF_8)
@Component
public class LoginResource {

    @Context
    ServletContext context;

    @Context
    private HttpServletRequest request;

    @Autowired
    private SystemService systemService;

    private static Logger logger = LoggerFactory.getLogger(LoginResource.class);

    private static JsonMapper mapper = JsonMapper.getInstance();

    /**
     * 用户登录
     *
     * @param userInfo
     * @param request
     * @return
     */
    @POST
    @Path("/login")
    public String Login(Map<String, String> userInfo, @Context HttpServletRequest request) {
        if (MapUtils.isEmpty(userInfo)) {
            return AppResultUtils.toString(Result.Status.BAD_REQUEST, "请求参数为空");
        }
        String userName = userInfo.get("userName");
        if (StringUtils.isBlank(userName)) {
            return AppResultUtils.toString(Result.Status.BAD_REQUEST, "用户名为空");
        }
        String password = userInfo.get("password");
        if (StringUtils.isBlank(password)) {
            return AppResultUtils.toString(Result.Status.BAD_REQUEST, "密码为空");
        }
        try {
            // shiro安全认证
            Subject subject = SecurityUtils.getSubject();
            String host = request.getRemoteHost();
            UsernamePasswordToken token = new UsernamePasswordToken(userName, password.toCharArray(), false, host, "");
            subject.login(token);
            // 更新用户信息
            User user = systemService.getUserByLoginName(userName);
            systemService.updateUserLoginInfo(user.getId());  // 更新登陆ip和时间
            UserUtils.putCache(UserUtils.CACHE_USER, user);
            Map<String, Object> responseMap = Maps.newHashMap();
            if (null != user.getOffice() && null != user.getOffice().getArea()) {
                if (Integer.parseInt(user.getOffice().getArea().getType()) <= 3) {
                    responseMap.put("status", Result.Status.OK.getCode());
                    responseMap.put("message", "登录成功");
                    responseMap.put("areaId", user.getOffice().getArea().getId());
                    responseMap.put("areaType", user.getOffice().getArea().getType());
                    responseMap.put("userId", user.getId());
                    responseMap.put("areaName", user.getOffice().getArea().getName());
                    //用户登陆之后的同一级
                    if (null == user.getOffice().getArea().getParent()) {
                        responseMap.put("officeName", "");
                    } else {
                        responseMap.put("officeName", user.getOffice().getArea().getName());
                    }

                    responseMap.put(Constant.JWT, "");
                } else {
                    responseMap.put("status", Result.Status.ERROR.getCode());
                    responseMap.put("message", "您没有权限使用该系统");
                }
            } else {
                return AppResultUtils.toString(Result.Status.INTERNAL_SERVER_ERROR, "用户登录发生异常");
            }
            return mapper.toJson(responseMap);
        } catch (UnknownAccountException uae) {
            return AppResultUtils.toString(Result.Status.BAD_REQUEST, "该用户不存在");
        } catch (IncorrectCredentialsException ice) {
            return AppResultUtils.toString(Result.Status.BAD_REQUEST, "密码错误");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return AppResultUtils.toString(Result.Status.INTERNAL_SERVER_ERROR, "用户登录发生异常");
        }
    }

}
