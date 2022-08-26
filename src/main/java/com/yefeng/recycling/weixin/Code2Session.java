package com.yefeng.recycling.weixin;

import cn.hutool.http.HttpUtil;

import java.util.HashMap;
import java.util.Map;


public final class Code2Session {

    //    请求地址
    static final String requestUrl = "https://api.weixin.qq.com/sns/jscode2session";

    public static  String getWxUserInfo(String code) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("appid", Constans.APPID);
        requestMap.put("secret", Constans.SECRET);
        requestMap.put("js_code", code);
        requestMap.put("grant_type", "authorization_code");
        String result = HttpUtil.get(requestUrl, requestMap);
        System.out.println(result);
        return result;
    }
}
