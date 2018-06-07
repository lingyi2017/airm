package com.infosoul.mserver.api.sys;

import com.google.common.collect.Maps;
import com.infosoul.mserver.api.Result;
import com.infosoul.mserver.common.mapper.JsonMapper;
import com.infosoul.mserver.common.utils.UserUtils;
import com.infosoul.mserver.common.web.MediaTypes;
import com.infosoul.mserver.entity.sys.User;
import com.infosoul.mserver.service.sys.SystemService;
import com.infosoul.mserver.webservice.rest.util.AppResultUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import java.util.Map;

/**
 * 用户RESTFull接口
 *
 * @author longxy
 * @date 2018-06-07 22:29
 */
@Path("/app/user")
@Consumes(MediaTypes.JSON_UTF_8)
@Produces(MediaTypes.JSON_UTF_8)
@Component
public class UserResource {

    @Context
    ServletContext context;

    @Autowired
    private SystemService userService;

    @Context
    private HttpServletRequest request;

    private static Logger logger = LoggerFactory.getLogger(UserResource.class);

    private static JsonMapper mapper = JsonMapper.getInstance();

    /**
     * 退出账户
     *
     * @param request
     * @return
     */
    @POST
    @Path("/logout")
    public Result<String> Logout(@Context HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            subject.logout();
            return Result.buildSuccessOkResult("退出账户成功");
        } else {
            return Result.buildErrorResult(Result.Status.INTERNAL_SERVER_ERROR, "用户注销发生异常!");
        }
    }


    /**
     * 个人资料查看
     *
     * @return
     */
    @GET
    @Path("/info")
    public String getUserInfo() {
        try {
            User user1 = UserUtils.getUser();
            if (StringUtils.isNotBlank(user1.getId())) {
                User user2 = userService.getUser(user1.getId());
                if (null != user2) {
                    Map<String, Object> map = Maps.newHashMap();
                    map.put("userId", user2.getId());
                    if (null != user2.getCompany()) {
                        map.put("company", user2.getCompany().getName());
                    } else {
                        map.put("company", "");
                    }
                    map.put("loginName", user2.getLoginName());
                    map.put("no", user2.getNo());
                    map.put("name", user2.getName());
                    map.put("email", user2.getEmail());
                    map.put("phone", user2.getPhone());
                    map.put("mobile", user2.getMobile());
                    map.put("status", Result.Status.OK.getCode());
                    map.put("message", "查看个人信息成功");
                    map.put("photo", user2.getPhoto());
                    return mapper.toJson(map);
                }
                return AppResultUtils.toString(Result.Status.BAD_REQUEST, "该用户不存在");
            }
            return AppResultUtils.toString(Result.Status.BAD_REQUEST, "该用户不存在");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return AppResultUtils.toString(Result.Status.INTERNAL_SERVER_ERROR, "请求出错，请重试");
        }
    }
}
