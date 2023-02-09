package com.yefeng.recycling.JWT;

import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.auth0.jwt.exceptions.*;
import com.yefeng.recycling.util.AccessAddressUtils;
import com.yefeng.recycling.util.JwtUtil;
import com.yefeng.recycling.util.TokenUtil;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
public class JWTFilter extends AuthenticatingFilter {
    private static final Log log = LogFactory.get();
    /**
     * 所有请求都会到这里来（无论是不是anon）。
     * 返回true：表示允许向下走。后边会走PathMatchingFilter，看路径是否对应anon等
     * 返回false：表示不允许向下走。
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest,
                                     ServletResponse servletResponse) throws Exception {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String token = TokenUtil.getToken(request);

        String ip = AccessAddressUtils.getIpAddress(request);


        String clientIP = ServletUtil.getClientIP((HttpServletRequest) servletRequest, null);
        log.info("clientIP:{} url:{} ,token:{}",clientIP, request.getRequestURI(), token);

        if (token == null || token.equals("null") || !StringUtils.hasText(token)) {
            //查看是否有refresh_token
            token = TokenUtil.getFlushToken(request);
            log.info("refresh_token:" + token);
            try {
                if (JwtUtil.verifyToken(token)) {
                    log.info("ip:{} url:{} ,refresh_token:{}", ip, request.getRequestURI(), token);
                    return true;
                }
            } catch (Exception e) {
                log.info("没有token,重定向到登录页面");
                HttpServletResponse a=  (HttpServletResponse) servletResponse;
//                a.sendRedirect("/error");
                response401(servletRequest, servletResponse, "没有token请先登录再使用");
                return true;
            }
            return true;
        } else {
            try {
//                //token查看token是否为黑名单
//                if (RedisUtils.isBlackList(token)) {
//                    log.info("ip:{} url:{} ,token{}在黑名单", ip, request.getRequestURI(), token);
//                    response401(servletRequest, servletResponse, "token已被黑名单");
//                    return false;
//                }
                boolean verified = JwtUtil.verifyToken(token);// 要么抛异常，要么返回true
                if (!verified) {
                    response401(servletRequest, servletResponse, "token无效");
                    return true;
                }
            } catch (AlgorithmMismatchException e) {
                e.printStackTrace();
                response401(servletRequest, servletResponse, "token错误");
            } catch (SignatureVerificationException e) {
                e.printStackTrace();
                response401(servletRequest, servletResponse, "签名验证失败");
            } catch (InvalidClaimException e) {
                e.printStackTrace();
                response401(servletRequest, servletResponse, "Claim无效");
            } catch (TokenExpiredException e) {
                //判断是否在黑名单是则移除(内置判断是否为黑名单)
//                RedisUtils.removeBlackList(token);
                //不在黑名单则尝试刷新token
//                String newToken = JwtUtil.refreshToken(token);
//                if (newToken == null || newToken.equals("") || !StringUtils.hasText(newToken)) {
                    response401(servletRequest, servletResponse, "token已过期");
                    return false;
//                }
//                RedisUtils.addBlackList(token);
                //刷新成功则重新设置token
//                TokenUtil.setToken(servletResponse, newToken);
//                log.info("token已过期,自动刷新token" + newToken);
//                return true;
            } catch (JWTDecodeException e) {
                log.warn("ip:{} url:{} ,token:{}被篡改", ip, request.getRequestURI(), token);
                response401(servletRequest, servletResponse, "token被篡改");
            }
        }
//        log.info("即将跳转登录页面");
//        // 此登录并非调用login接口，而是shiro层面的登录。
//        // 里边会调用下边的createToken方法
        return executeLogin(servletRequest, servletResponse);
    }


    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
//        httpServletResponse.setHeader("Access-control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equalsIgnoreCase("OPTIONS")) {
            httpServletResponse.setStatus(200);
            return false;
        }
        return super.preHandle(request, response);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {

        return super.isAccessAllowed(request, response, mappedValue);




    }

    /**
     * 这里的token会传给AuthorizingRealm子类（本处是AccountRealm）的doGetAuthenticationInfo方法作为参数
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest,
                                              ServletResponse servletResponse) {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String token = TokenUtil.getToken(request);

        if (!StringUtils.hasText(token)) {
            return null;
        }
        return new JWTToken(token);
    }

    private void response401(ServletRequest request, ServletResponse response
            , String msg) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        try {
            // //请求转发401controller
            httpServletRequest.getRequestDispatcher("/rbacManager/401/" + msg).forward(request, response);
        } catch (ServletException | IOException e) {
            log.error(e.getMessage());
        }
    }
}