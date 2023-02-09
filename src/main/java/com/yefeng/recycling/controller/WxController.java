package com.yefeng.recycling.controller;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.alibaba.fastjson.JSONObject;
import com.yefeng.recycling.BO.WxUserBO;
import com.yefeng.recycling.JWT.AccountProfile;
import com.yefeng.recycling.result.Result;
import com.yefeng.recycling.result.ResultUtil;
import com.yefeng.recycling.service.IUserService;
import com.yefeng.recycling.service.WxService;
import com.yefeng.recycling.weixin.Code2Session;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.shiro.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Tag(name = "wx-controller", description = "微信相关接口")
@RestController
@RequestMapping("/wx")
public class WxController {
    private static final Log log = LogFactory.get();

    @Operation(summary = "用户微信登录")
    @GetMapping("/getCode2Session")
    public Result getWxUserInfo(@Parameter(description = "微信临时登录code") String code) {

        log.info("code:  " + code);
        String wxUserInfo = Code2Session.getWxUserInfo(code);
        JSONObject json = JSONObject.parseObject(wxUserInfo);
        log.info("wxUserInfo:{}", wxUserInfo);
        log.info("json:{}", json);
        if (json.getIntValue("errcode") != 0)
            return ResultUtil.error().buildMessage("请求微信登录认证失败").buildData(json);
        String openid = json.getString("openid");
        String token = getToken(openid);
        log.info(token);
        if (!StringUtils.hasText(token)) {
            return ResultUtil.error().buildMessage("此用户还未注册").buildData(json);
        }

        json.put("token", token);
        log.info(json.toJSONString());
        return ResultUtil.success().buildMessage("关联系统用户成功").buildData(json);
    }

    @Resource
    WxService wxService;

    @Resource
    IUserService iUserService;

    public String getToken(String appid) {
        AccountProfile user = wxService.getUserByOpenId(appid);
        if (user == null) {
            return null;
        }
        return iUserService.getToken(Integer.valueOf(user.getId()), user.getUserName());
    }


    public Result registerUser(@RequestBody WxUserBO userBO){
        String userName = RandomUtil.randomString(10);
//        wxService.register();
        return null;
    }


}
