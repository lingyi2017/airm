package com.infosoul.mserver.api.app.sys;

import com.infosoul.mserver.api.BaseResource;
import com.infosoul.mserver.api.ResponseRest;
import com.infosoul.mserver.api.util.JWTUtils;
import com.infosoul.mserver.common.security.UsernamePasswordToken;
import com.infosoul.mserver.common.utils.UserUtils;
import com.infosoul.mserver.common.web.MediaTypes;
import com.infosoul.mserver.dto.api.LoginRpDTO;
import com.infosoul.mserver.dto.api.LoginRqDTO;
import com.infosoul.mserver.entity.sys.User;
import com.infosoul.mserver.service.sys.SystemService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

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
public class LoginResource extends BaseResource {

    @Autowired
    private SystemService systemService;

    /**
     * 用户登录
     *
     * @param requestDTO
     * @param request
     * @return
     */
    @POST
    @Path("/login")
    public ResponseRest Login(LoginRqDTO requestDTO, @Context HttpServletRequest request) {
        if (null == requestDTO || StringUtils.isEmpty(requestDTO.getUserName()) ||
                StringUtils.isEmpty(requestDTO.getPassword())) {
            return error(ResponseRest.Status.BAD_REQUEST);
        }

        try {
            // shiro安全认证
            Subject subject = SecurityUtils.getSubject();
            String host = request.getRemoteHost();
            UsernamePasswordToken shiroToken = new UsernamePasswordToken(requestDTO.getUserName(),
                    requestDTO.getPassword().toCharArray(), false, host, "");
            subject.login(shiroToken);

            // 更新用户信息
            User user = systemService.getUserByLoginName(requestDTO.getUserName());
            // 更新登陆ip和时间
            systemService.updateUserLoginInfo(user.getId());
            UserUtils.putCache(UserUtils.CACHE_USER, user);

            LoginRpDTO dto = buildLoginRp(user);
            return success(dto);
        } catch (UnknownAccountException uae) {
            return error(ResponseRest.Status.EXIST, "该用户不存在");
        } catch (IncorrectCredentialsException ice) {
            return error(ResponseRest.Status.BAD_REQUEST, "密码错误");
        } catch (Exception e) {
            logger.error("登录异常", e);
            return error(ResponseRest.Status.INTERNAL_SERVER_ERROR, "用户登录发生异常");
        }
    }

    private LoginRpDTO buildLoginRp(User user) {
        String token = JWTUtils.getToken(user);
        LoginRpDTO dto = new LoginRpDTO();
        dto.setToken(token);
        return dto;
    }

}
