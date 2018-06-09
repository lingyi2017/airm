package com.infosoul.mserver.api.app.filter;

import com.fasterxml.jackson.databind.JavaType;
import com.infosoul.mserver.api.ResponseRest;
import com.infosoul.mserver.api.Result;
import com.infosoul.mserver.api.util.AppResultUtils;
import com.infosoul.mserver.api.util.JWTUtils;
import com.infosoul.mserver.common.mapper.JsonMapper;
import com.infosoul.mserver.common.utils.Constant;
import com.infosoul.mserver.common.utils.UserUtils;
import com.infosoul.mserver.entity.sys.User;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.glassfish.jersey.server.ContainerRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class RestAuthFilter implements ContainerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestAuthFilter.class);

    private static final JsonMapper mapper = JsonMapper.getInstance();

    @Override
    public void filter(ContainerRequestContext requestContext)
            throws IOException {
        String uri = requestContext.getUriInfo().getPath();
        String requestMethod = requestContext.getMethod();
        // 仅拦截app端资源
        if (StringUtils.isNotBlank(uri) && uri.startsWith(Constant.APP)) {
            SecurityContext securityContext = requestContext.getSecurityContext();
            // 未认证
            if (securityContext == null || securityContext.getUserPrincipal() == null) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).
                        entity(ResponseRest.error(ResponseRest.Status.UNAUTHORIZED)).build());
                return;
            }
            String entity;
            // POST方式
            if (Constant.POST.equals(requestMethod)) {
                if (!(requestContext instanceof ContainerRequest)) {
                    requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).
                            entity(ResponseRest.error(ResponseRest.Status.UNKNOWN_ERROR)).build());
                    return;
                }
                ContainerRequest cr = (ContainerRequest) requestContext;
                cr.bufferEntity();
                // 从流中获取参数信息
                BufferedReader reader = null;
                StringBuilder sb = new StringBuilder();
                try {
                    String line;
                    reader = new BufferedReader(new InputStreamReader(cr.getEntityStream(), "UTF-8"));
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                } catch (IOException e) {
                    LOGGER.error("rest认证IOException1", e);
                    requestContext.abortWith(Response.status(Response.Status.INTERNAL_SERVER_ERROR).
                            entity(ResponseRest.error(ResponseRest.Status.INTERNAL_SERVER_ERROR)).build());
                } finally {
                    if (reader != null) {
                        try {
                            // 关闭文件流
                            reader.close();
                        } catch (IOException e) {
                            LOGGER.error("rest认证IOException2", e);
                            requestContext.abortWith(Response.status(Response.Status.INTERNAL_SERVER_ERROR).
                                    entity(ResponseRest.error(ResponseRest.Status.INTERNAL_SERVER_ERROR)).build());
                        }
                    }
                }

                // 获取 token 信息
                if (StringUtils.isBlank(sb.toString())) {
                    requestContext.abortWith(Response.status(Response.Status.BAD_REQUEST).
                            entity(ResponseRest.error(ResponseRest.Status.BAD_REQUEST)).build());
                    return;
                }
                JavaType javaType = mapper.createMapType(Map.class, String.class, Object.class);
                // 转为map对象
                Map<String, Object> map = mapper.fromJson(sb.toString(), javaType);
                // map为空
                if (MapUtils.isEmpty(map)) {
                    requestContext.abortWith(Response.status(Response.Status.BAD_REQUEST).
                            entity(ResponseRest.error(ResponseRest.Status.BAD_REQUEST)).build());
                    return;
                }
                // token不存在
                if ((map.get(Constant.TOKEN) == null) || StringUtils.isBlank(map.get(Constant.TOKEN).toString())) {
                    requestContext.abortWith(Response.status(Response.Status.BAD_REQUEST).
                            entity(ResponseRest.error(ResponseRest.Status.BAD_REQUEST, "token 不存在")).build());
                    return;
                }

                String token = map.get(Constant.TOKEN).toString();
                authToken(requestContext, token);
            } else if (Constant.GET.equals(requestMethod)) {
                List<String> jwtList = requestContext.getUriInfo().getQueryParameters().get(Constant.TOKEN);
                if (CollectionUtils.isEmpty(jwtList)) {
                    requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).
                            entity(ResponseRest.error(ResponseRest.Status.UNAUTHORIZED)).build());
                    return;
                }
                String token = jwtList.get(0);
                authToken(requestContext, token);
            } else {
                entity = AppResultUtils.toString(Result.Status.METHOD_NOT_ALLOWED, Result.Status.METHOD_NOT_ALLOWED.getReason());
                requestContext.abortWith(Response.status(Response.Status.METHOD_NOT_ALLOWED).entity(entity).build());
            }
        }
    }

    /**
     * token 认证
     *
     * @param requestContext
     * @param token
     */
    private void authToken(ContainerRequestContext requestContext, String token) {
        User user = UserUtils.getUser();
        // 用户不存在
        if (user == null || StringUtils.isBlank(user.getId())) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).
                    entity(ResponseRest.error(ResponseRest.Status.UNAUTHORIZED)).build());
            return;
        }
        Boolean isFormat = JWTUtils.formatValidation(token);
        if (!isFormat) {
            requestContext.abortWith(Response.status(Response.Status.BAD_REQUEST).
                    entity(ResponseRest.error(ResponseRest.Status.BAD_REQUEST, "token 格式错误")).build());
            return;
        }
        try {
            // 用户的密码作为密钥
            String secretKey = user.getPassword();
            Map<String, Object> header = JWTUtils.getHeader(token, secretKey);
            Map<String, Object> claims = JWTUtils.getClaims(token, secretKey);
            String serverJWT = JWTUtils.createJWT(header, claims, secretKey);
            // jwt 不正确
            if (!token.equals(serverJWT)) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).
                        entity(ResponseRest.error(ResponseRest.Status.UNAUTHORIZED)).build());
                return;
            }
            // 过期时间
            long exp = Long.parseLong(claims.get(Constant.EXP).toString());
            long current = System.currentTimeMillis();
            // token过期
            if (current > exp) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).
                        entity(ResponseRest.error(ResponseRest.Status.UNAUTHORIZED, "token已失效")).build());
            }
        } catch (Exception e) {
            LOGGER.error("rest认证异常", e);
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).
                    entity(ResponseRest.error(ResponseRest.Status.UNAUTHORIZED, "token被篡改")).build());
        }
    }

}
