package com.infosoul.mserver.api.filter;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.JavaType;
import com.infosoul.mserver.api.Result;
import com.infosoul.mserver.api.util.JWTUtils;
import com.infosoul.mserver.common.mapper.JsonMapper;
import com.infosoul.mserver.common.utils.Constant;
import com.infosoul.mserver.common.utils.UserUtils;
import com.infosoul.mserver.entity.sys.User;
import com.infosoul.mserver.webservice.rest.util.AppResultUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.glassfish.jersey.server.ContainerRequest;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

/**
 * RESTful 认证过滤类
 *
 * @author longxy
 * @version V1.0
 * @date 2016年2月23日 下午2:15:00
 */
@Provider
@Priority(Priorities.AUTHENTICATION)
public class RestAuthenticationFilter implements ContainerRequestFilter {

    private static JsonMapper mapper = JsonMapper.getInstance();

    @Override
    public void filter(ContainerRequestContext requestContext)
            throws IOException {
        String uri = requestContext.getUriInfo().getPath();
        String method = requestContext.getMethod();
        if (StringUtils.isNotBlank(uri) && uri.startsWith(Constant.APP)) {  // 仅拦截app端资源
            SecurityContext securityContext = requestContext.getSecurityContext();
            if (securityContext == null || securityContext.getUserPrincipal() == null) {  // 未认证
                String result = AppResultUtils.toString(Result.Status.UNAUTHORIZED, Result.Status.UNAUTHORIZED.getReason());
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity(result).build());
                return;
            }
            String entity = null;
            String jwt = null;
            if (Constant.POST.equals(method)) {  // POST方式
                if (!(requestContext instanceof ContainerRequest)) {
                    return;
                }
                ContainerRequest cr = (ContainerRequest) requestContext;
                cr.bufferEntity();  // KEY CODE

                // 从流中获取参数信息
                BufferedReader reader = null;
                StringBuilder sb = new StringBuilder();
                String line = null;
                try {
                    reader = new BufferedReader(new InputStreamReader(cr.getEntityStream(), "UTF-8"));
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    entity = AppResultUtils.toString(Result.Status.INTERNAL_SERVER_ERROR, Result.Status.INTERNAL_SERVER_ERROR.getReason());
                    requestContext.abortWith(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(entity).build());
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();  // 关闭文件流
                        } catch (IOException e) {
                            e.printStackTrace();
                            entity = AppResultUtils.toString(Result.Status.INTERNAL_SERVER_ERROR, Result.Status.INTERNAL_SERVER_ERROR.getReason());
                            requestContext.abortWith(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(entity).build());
                        }
                    }
                }

                // 获取 token 信息
                if (StringUtils.isBlank(sb.toString())) {
                    entity = AppResultUtils.toString(Result.Status.BAD_REQUEST, "请求参数为空");
                    requestContext.abortWith(Response.status(Response.Status.BAD_REQUEST).entity(entity).build());
                    return;
                }
                JavaType javaType = mapper.createMapType(Map.class, String.class, Object.class);
                Map<String, Object> map = mapper.fromJson(sb.toString(), javaType);  // 转为map对象
                if (MapUtils.isEmpty(map)) {  // map为空
                    entity = AppResultUtils.toString(Result.Status.BAD_REQUEST, "请求参数格式错误");
                    requestContext.abortWith(Response.status(Response.Status.BAD_REQUEST).entity(entity).build());
                    return;
                }
                if ((map.get(Constant.JWT) == null) || StringUtils.isBlank(map.get(Constant.JWT).toString())) {  // jwt不存在
                    entity = AppResultUtils.toString(Result.Status.BAD_REQUEST, "jwt 不存在");
                    requestContext.abortWith(Response.status(Response.Status.BAD_REQUEST).entity(entity).build());
                    return;
                }
                jwt = map.get(Constant.JWT).toString();
                authcJWT(requestContext, jwt);
            } else if (Constant.GET.equals(method)) {  // GET方式
                List<String> jwtList = requestContext.getUriInfo().getQueryParameters().get(Constant.JWT);
                if (CollectionUtils.isEmpty(jwtList)) {
                    entity = AppResultUtils.toString(Result.Status.UNAUTHORIZED, "jwt 不存在");
                    requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity(entity).build());
                    return;
                }
                jwt = jwtList.get(0);
                authcJWT(requestContext, jwt);
            } else {
                entity = AppResultUtils.toString(Result.Status.METHOD_NOT_ALLOWED, Result.Status.METHOD_NOT_ALLOWED.getReason());
                requestContext.abortWith(Response.status(Response.Status.METHOD_NOT_ALLOWED).entity(entity).build());
            }
        }
    }

    /**
     * jwt 认证
     *
     * @param requestContext
     * @param jwt
     */
    private void authcJWT(ContainerRequestContext requestContext, String jwt) {
        String entity = null;
        User currentUser = UserUtils.getUser();
        if (currentUser == null || StringUtils.isBlank(currentUser.getId())) {  // 用户不存在
            entity = AppResultUtils.toString(Result.Status.UNAUTHORIZED, Result.Status.UNAUTHORIZED.getReason());
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity(entity).build());
            return;
        }
        Boolean isFormat = JWTUtils.formatValidation(jwt);
        if (!isFormat) {
            entity = AppResultUtils.toString(Result.Status.BAD_REQUEST, "jwt 格式错误");
            requestContext.abortWith(Response.status(Response.Status.BAD_REQUEST).entity(entity).build());
            return;
        }
        try {
            String secretKey = currentUser.getPassword();  // 用户的密码作为 密钥
            Map<String, Object> header = JWTUtils.getHeader(jwt, secretKey);
            Map<String, Object> claims = JWTUtils.getClaims(jwt, secretKey);
            String serverJWT = JWTUtils.createJWT(header, claims, secretKey);
            if (!jwt.equals(serverJWT)) {  // jwt 不正确
                entity = AppResultUtils.toString(Result.Status.UNAUTHORIZED, "jwt 认证失败");
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity(entity).build());
                return;
            }
            long exp = Long.parseLong(claims.get(Constant.EXP).toString());  // 过期时间
            long current = System.currentTimeMillis();
            if (current > exp) {  // jwt 过期
                entity = AppResultUtils.toString(Result.Status.UNAUTHORIZED, "jwt 已失效，请重新登录");
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity(entity).build());
            }
        } catch (Exception e) {
            e.printStackTrace();
            entity = AppResultUtils.toString(Result.Status.UNAUTHORIZED, "jwt 被篡改");
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity(entity).build());
        }
    }

}
