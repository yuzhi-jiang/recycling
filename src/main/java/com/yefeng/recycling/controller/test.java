package com.yefeng.recycling.controller;

import com.yefeng.recycling.weixin.Code2Session;

//@RequestMapping("/test")
//@RestController
public class test {

//    @GetMapping("/getConfig")
    public String getConfig() {
        return "fsssf";
    }
    Code2Session code2Session = new Code2Session();
//    @GetMapping("/getCode2Session")
    public String getWxUserInfo(String code){
        return code2Session.getWxUserInfo(code);
    }
}
