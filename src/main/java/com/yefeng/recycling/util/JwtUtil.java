package com.yefeng.recycling.util;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.yefeng.recycling.JWT.JwtProperties;
import com.yefeng.recycling.shiro.ApplicationContextHolder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class JwtUtil {
    @Resource
    private static final JwtProperties jwtProperties;

    static {
        jwtProperties = ApplicationContextHolder.getContext().getBean(JwtProperties.class);
    }
    private static final Log log=LogFactory.get();
    // 创建jwt token
    public static String createToken(String userId, String userName) {
        try {
            Date date = new Date(System.currentTimeMillis() + jwtProperties.getExpire() * 1000);
            Algorithm algorithm = Algorithm.HMAC512(jwtProperties.getSecret());
            return JWT.create()
                    // 自定义私有的payload的key-value。比如：.withClaim("userName", "Tony")
                    .withClaim("username", userName)
                    .withAudience(userId)  // 将 user id 保存到 token 里面
                    .withExpiresAt(date)   // date之后，token过期
                    .sign(algorithm);      // token 的密钥
        } catch (Exception e) {
            return null;
        }
    }



    // 创建jwt token
    public static String createToken(String userName) {
        try {
            Date date = new Date(System.currentTimeMillis() + jwtProperties.getExpire() * 1000);
            Algorithm algorithm = Algorithm.HMAC512(jwtProperties.getSecret());
            return JWT.create()
                    // 自定义私有的payload的key-value。比如：.withClaim("userName", "Tony")
                    .withClaim("username", userName)
                    .withExpiresAt(date)   // date之后，token过期
                    .sign(algorithm);      // token 的密钥
        } catch (Exception e) {
            return null;
        }
    }

    // 创建jwt token
    public static String createToken(String userId, String userName, String[] roles) {
        try {
            Date date = new Date(System.currentTimeMillis() + jwtProperties.getExpire() * 1000);//转为毫秒
            log.info("过期时间为" + date.getTime());
            log.info("过期时间为" + jwtProperties.getExpire() * 1000);
            Algorithm algorithm = Algorithm.HMAC512(jwtProperties.getSecret());
            return JWT.create()
                    .withArrayClaim("roles", roles)
                    // 自定义私有的payload的key-value。比如：.withClaim("userName", "Tony")
                    .withClaim("username", userName)
                    .withAudience(userId)  // 将 user id 保存到 token 里面
                    .withExpiresAt(date)   // date之后，token过期
                    .sign(algorithm);      // token 的密钥
        } catch (Exception e) {
            return null;
        }
    }

    // 创建jwt token
    public static String createToken(HashMap<String, String> claimMap, String... audience) {
        try {
            Date date = new Date(System.currentTimeMillis() + jwtProperties.getExpire() * 1000);
            Algorithm algorithm = Algorithm.HMAC512(jwtProperties.getSecret());

            JWTCreator.Builder builder = JWT.create();
            for (String key : claimMap.keySet()) {
                builder.withClaim(key, claimMap.get(key));
            }
            builder.withAudience(audience);
            builder.withExpiresAt(date);
            return builder.sign(algorithm);
//            return JWT.create()
//                    // 自定义私有的payload的key-value。比如：.withClaim("userName", "Tony")
////                    .withClaim("username", userName)
//                    .withAudience(userId)  // 将 user id 保存到 token 里面
//                    .withExpiresAt(date)   // date之后，token过期
//                    .sign(algorithm);      // token 的密钥


        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 校验token
     * 若校验失败，会抛出异常：{@link JWTVerificationException}
     * 失败情况（按先后顺序）：
     * - 算法不匹配：{@link com.auth0.jwt.exceptions.AlgorithmMismatchException}
     * - 签名验证失败：{@link com.auth0.jwt.exceptions.SignatureVerificationException}
     * - Claim无效：{@link com.auth0.jwt.exceptions.InvalidClaimException}
     * - token超期：{@link com.auth0.jwt.exceptions.TokenExpiredException}
     */
    public static boolean verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC512(jwtProperties.getSecret());

            JWTVerifier jwtVerifier = JWT.require(algorithm)
                    // .withIssuer("auth0")
                    // .withClaim("userName", userName)
                    .build();

            DecodedJWT jwt = jwtVerifier.verify(token);
        } catch (Exception e) {
            log.warn(e.getMessage());
            throw e;
//            e.printStackTrace();
//            return false;
        }
        return true;
    }

    // getAudienceBykey
    public static String getAudienceByKey(String token, String key) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            List<String> audience = jwt.getAudience();
            return audience.get(audience.indexOf(key));
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    public static String getUserIdByToken(String token) {
        try {
            return JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    public static String getUserName(String token) {
        try {
            return JWT.decode(token).getClaim("username").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    public static <T> T getValueByKey(String token, String key, Class<T> var1) {
        try {
            return JWT.decode(token).getClaim(key).as(var1);
        } catch (JWTDecodeException e) {
            return null;
        }
    }


    public static String[] getRoles(String token) {
        ArrayList roles =JwtUtil.getValueByKey(token, "roles",ArrayList.class);
        int size = roles.size();
        if (size <= 0) {
            return null;
        }
        String[] roleArray = new String[size];
        for (int i = 0; i < size; i++) {
            roleArray[i] = roles.get(i).toString();
        }
        return roleArray;
    }

    public static boolean hasRole(HttpServletRequest request,String role){
        try {
            String token = TokenUtil.getToken(request);
            String[] roles = getRoles(token);
            for (String s : roles) {
                if (s.contains(role)){
                    return true;
                }
            }
        } catch (Exception e) {
            log.warn("获取权限失败,报错如下:\n"+e.getMessage());
            e.printStackTrace();
        }
        return  false;
    }

    public static boolean isTokenExpired(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        return decodedJWT.getExpiresAt().before(new Date());
    }

    public static boolean isTokenExpiredDays(String token, Integer days) {
        //获取一条后的时间
        DecodedJWT decodedJWT = JWT.decode(token);
        long expTime = decodedJWT.getExpiresAt().getTime();
        Date daysDate = new Date(expTime + days * 24 * 60 * 60 * 1000);
        return daysDate.before(new Date());
    }


    //刷新token
    public static String refreshToken(String token) {
        if (!isTokenExpired(token)) {//未过期
            return token;
        }
        if (isTokenExpiredDays(token, jwtProperties.getRefreshExpire())) {//已经过了期刷新时间
            return "";
        }
        String userId = getUserIdByToken(token);
        String userName = getUserName(token);
        String[] roles = getRoles(token);
        return createToken( userId,userName,  roles);
    }


    //检查用户是否有权限访问某个资源 //TODO 检验是否本人
    public static boolean checkPermission(String token, String userName) {
        String name = getValueByKey(token, userName, String.class);
        if (name == null) {
            return false;
        }
        return name.equals(userName);
    }

}