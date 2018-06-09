package com.infosoul.mserver.api.util;

import com.google.common.collect.Maps;
import com.infosoul.mserver.common.config.Global;
import com.infosoul.mserver.common.utils.Constant;
import com.infosoul.mserver.common.utils.DateUtils;
import com.infosoul.mserver.entity.sys.User;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.Map;

/**
 * JWT 工具类
 *
 * @author longxy
 * @version V1.0
 * @date 2016年2月2日 下午3:25:13
 */
public class JWTUtils {

    /**
     * 生成 JWT
     *
     * @param header
     * @param claims
     * @param secretKey
     * @return
     */
    public static String createJWT(Map<String, Object> header, Map<String, Object> claims, String secretKey) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(secretKey);
        Key signingKey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());
        JwtBuilder builder = Jwts.builder().
                setHeader(header).setClaims(claims).
                signWith(signatureAlgorithm, signingKey);
        String jwt = builder.compact();
        return jwt;
    }

    /**
     * 获取 头部信息
     *
     * @param jwt
     * @param secretKey
     * @return
     */
    public static Map<String, Object> getHeader(String jwt, String secretKey) {
        try {
            Map<String, Object> header = Jwts.parser().
                    setSigningKey(DatatypeConverter.parseBase64Binary(secretKey)).
                    parseClaimsJws(jwt).getHeader();
            return header;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取 载荷信息
     *
     * @param jwt
     * @param secretKey
     * @return
     */
    public static Map<String, Object> getClaims(String jwt, String secretKey) {
        try {
            Map<String, Object> claims = Jwts.parser().
                    setSigningKey(DatatypeConverter.parseBase64Binary(secretKey)).
                    parseClaimsJws(jwt).getBody();
            return claims;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取 签名
     *
     * @param jwt
     * @param secretKey
     * @return
     */
    public static String getSignature(String jwt, String secretKey) {
        try {
            String signature = Jwts.parser().
                    setSigningKey(DatatypeConverter.parseBase64Binary(secretKey)).
                    parseClaimsJws(jwt).getSignature();
            return signature;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取 用户名
     *
     * @param jwt
     * @param secretKey
     * @return
     */
    public static String getUserName(String jwt, String secretKey) {
        try {
            Map<String, Object> claims = getClaims(jwt, secretKey);
            String userName = (String) claims.get(Constant.USER_NAME);
            return userName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * jwt 格式验证
     * jwt 格式: AABBCC.DDEEFF.GGHHII
     *
     * @param jwt
     * @return 格式正确-true; 格式错误-false
     */
    public static Boolean formatValidation(String jwt) {
        try {
            String[] jwtArr = jwt.trim().split("\\.");
            int length = jwtArr.length;
            if (length == 3) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * jwt 认证
     * 适用范围 : RestAuthenticationFilter 过滤器无法认证的资源
     *
     * @param jwt
     * @param user
     * @return
     */
    public static Boolean authcJWT(String jwt, User user) {
        Boolean isFormat = formatValidation(jwt);
        if (!isFormat) {
            return false;
        }
        try {
            // 用户的密码作为 密钥
            String secretKey = user.getPassword();
            Map<String, Object> header = getHeader(jwt, secretKey);
            Map<String, Object> claims = getClaims(jwt, secretKey);
            String serverJWT = createJWT(header, claims, secretKey);
            // jwt 不正确
            if (!jwt.equals(serverJWT)) {
                return false;
            }
            // 过期时间
            long exp = Long.parseLong(claims.get(Constant.EXP).toString());
            long current = System.currentTimeMillis();
            // jwt 过期
            if (current > exp) {
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取令牌
     *
     * @param user
     * @return
     */
    public static String getToken(User user) {
        Map<String, Object> header = Maps.newHashMap();
        header.put(Header.TYPE, "JWT");

        Map<String, Object> claims = Maps.newHashMap();
        claims.put(Constant.USER_ID, user.getId());
        claims.put(Constant.USER_NAME, user.getLoginName());
        int min = Integer.parseInt(Global.getConfig("jwt.exp"));
        long exp = DateUtils.addMin(new Date(), min);
        // 过期时间
        claims.put(Constant.EXP, exp);
        return JWTUtils.createJWT(header, claims, user.getPassword());
    }
}
