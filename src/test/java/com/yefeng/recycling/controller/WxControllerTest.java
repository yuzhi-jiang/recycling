package com.yefeng.recycling.controller;

import com.yefeng.recycling.JWT.AccountProfile;
import com.yefeng.recycling.service.IUserService;
import com.yefeng.recycling.service.WxService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class WxControllerTest {
    @Resource
    IUserService iUserService;

    @Resource
    WxService wxService;
    @Test
    void getToken() {
        String  appid="oZ7a05bdTH1gabyU_gLC3VuoUuxE";
        AccountProfile user = wxService.getUserByOpenId(appid);
        if (user==null){
            System.out.println("用户不在系统中");
            return;
        }
        System.out.println("user"+user.toString());
        String token = iUserService.getToken(Integer.valueOf(user.getId()), user.getUserName());
        System.out.println("token"+token);

    }
}