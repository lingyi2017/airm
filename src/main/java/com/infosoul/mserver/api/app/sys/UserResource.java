package com.infosoul.mserver.api.app.sys;

import com.infosoul.mserver.api.BaseResource;
import com.infosoul.mserver.api.ResponseRest;
import com.infosoul.mserver.common.utils.UserUtils;
import com.infosoul.mserver.common.web.MediaTypes;
import com.infosoul.mserver.dto.api.UserRpDTO;
import com.infosoul.mserver.entity.sys.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;

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
public class UserResource extends BaseResource {

    /**
     * 获取用户信息
     *
     * @return
     */
    @GET
    @Path("/info")
    public ResponseRest getUserInfo() {
        try {
            User user = UserUtils.getUser();
            if (null == user) {
                error(ResponseRest.Status.NOT_EXIST, "用户信息不存在");
            }
            UserRpDTO dto = new UserRpDTO();
            BeanUtils.copyProperties(user, dto);
            return success(dto);
        } catch (Exception e) {
            logger.error("获取用户信息异常", e.getMessage());
            return error(ResponseRest.Status.INTERNAL_SERVER_ERROR, "获取用户信息异常");
        }
    }

    /**
     * 退出账户
     *
     * @return
     */
    @POST
    @Path("/logout")
    public ResponseRest logout() {
        Subject subject = SecurityUtils.getSubject();
        try {
            // TODO 设置token的过期时间为0
            subject.logout();
            return success();
        } catch (Exception e) {
            logger.error("用户注销异常", e);
            return error(ResponseRest.Status.INTERNAL_SERVER_ERROR, "用户注销发生异常");
        }
    }
}
