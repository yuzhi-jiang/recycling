package com.yefeng.recycling.publicController;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.yefeng.recycling.handller.MyTokenException;
import com.yefeng.recycling.result.Result;
import com.yefeng.recycling.result.ResultUtil;
import com.yefeng.recycling.util.JwtUtil;
import com.yefeng.recycling.util.TokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Tag(name = "api-controller",description = "api")
@RestController
@RequestMapping("/api")
public class apiController {

    private static final Log log = LogFactory.get();

    private void response401(ServletRequest request, ServletResponse response
            , String msg) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        try {
            // //请求转发401controller
            httpServletRequest.getRequestDispatcher("/rbacManager/401/" + msg).forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

//
//    @GetMapping("/reflushToken")
//    public Result reFlushToken(HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
//        String token = TokenUtil.getToken(servletRequest);
//        String ip = AccessAddressUtils.getIpAddress(servletRequest);
//        try {
//            JwtUtil.verifyToken(token);
//        } catch (AlgorithmMismatchException e) {
//            e.printStackTrace();
//            response401(servletRequest, servletResponse, "token错误");
//        } catch (SignatureVerificationException e) {
//            e.printStackTrace();
//            response401(servletRequest, servletResponse, "签名验证失败");
//        } catch (InvalidClaimException e) {
//            e.printStackTrace();
//            response401(servletRequest, servletResponse, "Claim无效");
//        } catch (TokenExpiredException e) {
//            response401(servletRequest, servletResponse, "token已过期");
//        } catch (
//                JWTDecodeException e) {
//            log.warn("ip:{} url:{} ,token:{}被篡改", ip, servletRequest.getRequestURI(), token);
//            response401(servletRequest, servletResponse, "token被篡改");
//        }
//    }

    @Operation(summary = "刷新token")
    @PostMapping("/flushToken")
    public Result updateToken(HttpServletRequest request,HttpServletResponse response) {
        String token=TokenUtil.getFlushToken(request);
        try {
            JwtUtil.verifyToken(token);
        } catch (TokenExpiredException e) {
            System.out.println("token过期");
            System.out.println("即将刷新token");
        } catch (Exception e) {
            throw new MyTokenException("token错误");
        }


//        //是否在黑名单中
//        if (RedisUtils.isBlackList(token)) {
//            return  Result.failure("token已失效");
//        }

        boolean tokenExpired = JwtUtil.isTokenExpired(token);
        if (tokenExpired) {
//            //token过期未超过一天
//            if (JwtUtil.isTokenExpiredDays(token, 1)) {
//                System.out.println("token已经过了刷新时间");
//                return new Result().setErrCode(401).setErrMsg("token已经过了刷新时间,请重新登录");
//            }
////            创建新的token
//            String userName = JwtUtil.getUserName(token);
//            MyUserDetail userDetail = service.getUserDetail(userName);
//            String[] arrayRoles = userDetail.getArrayRoles();
//            String SToken = JwtUtil.createToken(String.valueOf(userDetail.getId()), userName, arrayRoles);

            String SToken = JwtUtil.refreshToken(token);
            if (SToken == null||SToken.equals("")||!StringUtils.hasText(SToken)) {
                return  ResultUtil.fail().buildMessage("token已经过了刷新时间,请重新登录");
            }

            TokenUtil.setToken(response, SToken);

            //旧的token移入黑名单
//            RedisUtils.addBlackList(token);

            return ResultUtil.success().buildMessage("token刷新成功").buildData(SToken);
        }
        //token未过期
        JSONObject token1 = new JSONObject().fluentPut("token", token);
        return  ResultUtil.success().buildMessage("token未过期").buildData(token1);
    }



}
