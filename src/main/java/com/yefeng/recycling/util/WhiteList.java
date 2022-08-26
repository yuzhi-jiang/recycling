package com.yefeng.recycling.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//白名单
public interface WhiteList {
    List<String> KNIFE4J = Arrays.asList(
            "/",
            "/favicon.ico",
            "/login",
            "/userLogin",
            "/register",


            "/401",
            "/403",
            "/404",
            "/error",

            "/rbacManager/401/**",
            "/js/**",
            "/css/**",
            "/img/**"
    );

    List<String> ALL = new ArrayList<>(KNIFE4J);

    public static void main(String[] args) {
        List<String> all = WhiteList.ALL;
        if (all.contains("/client/Reception_index")) {
            System.out.println("/client/Reception_index在白名单");

        }
    }
}