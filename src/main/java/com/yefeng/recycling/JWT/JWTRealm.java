package com.yefeng.recycling.JWT;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.yefeng.recycling.VO.RolePowerVO;
import com.yefeng.recycling.VO.UserRoleVO;
import com.yefeng.recycling.entity.Power;
import com.yefeng.recycling.service.IPowerService;
import com.yefeng.recycling.service.UserDetailService;
import com.yefeng.recycling.util.JwtUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class JWTRealm extends AuthorizingRealm {
    // 让shiro支持我们自定义的token，即如果传入的token时JWTToken则放行
    // 必须重写不然shiro会报错

    @Lazy
    @Autowired
    UserDetailService userDetailService;

    @Lazy
    @Autowired
    IPowerService powerService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    private static final Log log = LogFactory.get();


    /*
        授权,真正有权限控制的时候才会来到这里
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        AccountProfile accountProfile = (AccountProfile) principalCollection.getPrimaryPrincipal();
        log.info("进入授权 accountProfile:{}",accountProfile);
        String userName = accountProfile.getUserName();

        UserRoleVO userDetail = userDetailService.getUserDetail(userName);


        Set<RolePowerVO> rolePowerVOSet = userDetail.getRoles();

        SimpleAuthorizationInfo authInfo = new SimpleAuthorizationInfo();


        HashSet<Power> powerHashSet = new HashSet<>();

        Iterator<RolePowerVO> iterator = rolePowerVOSet.iterator();

        while (iterator.hasNext()) {
            RolePowerVO next = iterator.next();
            Set<Power> powerByRoleId = powerService.getPowerByRoleId(next.getId());
            powerHashSet.addAll(powerByRoleId);
        }


//        rolePowerVOSet.forEach(roleVO -> {
//            Set<Power> powerByRoleId = powerService.getPowerByRoleId(roleVO.getId());
//            powerHashSet.addAll(powerByRoleId);
//        });



        //添加角色
        rolePowerVOSet.forEach(roleVO -> authInfo.addRole(roleVO.getRoleKey()));

        //添加权限
        powerHashSet.forEach(power -> authInfo.addStringPermission(power.getPower()));

        log.info(powerHashSet.toString());
        return authInfo;
    }

    /*
     认证，不授权
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException, SignatureVerificationException, TokenExpiredException {
        JWTToken jwtToken = (JWTToken) authenticationToken;
        String token = jwtToken.getPrincipal().toString();


        log.info("进入验证流程 token:{}",token);

        JwtUtil.verifyToken(token);

        if (token == null || token.isBlank()) {
            throw new AuthenticationException("token为空!");
        }
        // 解密获得username，用于和数据库进行对比
        String username = JwtUtil.getUserName(token);
        if (username == null) {
            throw new UnknownAccountException("token为空，请重新登录");
        }
//        MyUserDetail userBean = userService.getUserDetail(username);
        UserRoleVO userDetail = userDetailService.getUserDetail(username);

        AccountProfile accountProfile = new AccountProfile();
        accountProfile.setId(String.valueOf(userDetail.getUserInfo().getId()));
        accountProfile.setUserName(userDetail.getUserInfo().getUserName());
        log.info("doGetAuthenticationInfo 进行认证，检查用户名密码,返回认证信息,userBean:{}", userDetail);
        return new SimpleAuthenticationInfo(accountProfile, jwtToken.getCredentials(), getName());
    }
}
