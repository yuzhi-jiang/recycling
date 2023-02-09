package com.yefeng.recycling.controller;

import com.yefeng.recycling.result.Result;
import com.yefeng.recycling.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserControllerTest {
    @Resource
    IUserService userService;
    @Test
    void login() {
        String userName = "yefeng";
        String password = "jiangshao";

        Result login = userService.login(userName, password);
        System.out.println(login);

    }
}