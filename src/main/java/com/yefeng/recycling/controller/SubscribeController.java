package com.yefeng.recycling.controller;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.yefeng.recycling.entity.Subscribe;
import com.yefeng.recycling.result.Result;
import com.yefeng.recycling.result.ResultUtil;
import com.yefeng.recycling.service.ISubscribeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yefeng
 * @since 2022-08-23
 */
@Tag(name = "subscribe-controller", description = "预约模=模块")
@RestController
@RequestMapping("/subscribe")
public class SubscribeController {
    private static final Log log= LogFactory.get();
    @Resource
    ISubscribeService iSubscribeService;

    @Operation(summary = "获取所有预约,需要管理员和超级管理员才能访问")
    @RequiresRoles(value = {"ROLE_ROOT", "ROOT_ADMIN"},logical = Logical.OR)
    @GetMapping("/all")
    public Result getAllSubscribe() {
        ArrayList<Subscribe> subscribes = iSubscribeService.getAllSubscribe();
        log.info(subscribes.toString());
        return ResultUtil.success().buildData(subscribes);
    }
    /*
    根据用户id获取预约
     */

    @Operation(summary = "用户查看自己的所有预约,无权限设定，当需要登录即token")
    @GetMapping("/getSubscribe")
    public Result getSubscribeByUserId(Integer userId){
        ArrayList<Subscribe> subscribes = iSubscribeService.getSubscribe(userId);
        log.info(subscribes.toString());
        return ResultUtil.success().buildData(subscribes);
    }

}
