package com.yefeng.recycling.controller;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yefeng.recycling.BO.SubscribeBO;
import com.yefeng.recycling.entity.Subscribe;
import com.yefeng.recycling.result.Result;
import com.yefeng.recycling.result.ResultUtil;
import com.yefeng.recycling.service.ISubscribeService;
import com.yefeng.recycling.util.StatusConstant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

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
    private static final Log log = LogFactory.get();
    @Resource
    ISubscribeService iSubscribeService;

    @Operation(summary = "获取所有预约,需要管理员和超级管理员才能访问")
    @RequiresRoles(value = {"ROLE_ROOT", "ROOT_ADMIN"}, logical = Logical.OR)
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
    @GetMapping("/user/{userId}")
    public Result getSubscribeByUserId(@PathVariable Integer userId) {
        ArrayList<Subscribe> subscribes = iSubscribeService.getSubscribe(userId);
        log.info(subscribes.toString());
        return ResultUtil.success().buildData(subscribes);
    }


    @Operation(summary = "提交预约", description = "一经提交，普通用户/匿名用户(暂时未放行)，不可修改，请前台提示用户，严格检查表单")
    @PostMapping("/")
    public Result saveSubscribe(@RequestBody SubscribeBO subscribeBO) {

        boolean flag = iSubscribeService.saveSubscribe(subscribeBO);
        if (flag)
            return ResultUtil.success().buildMessage("提交预约成功,请耐心等待");

        log.warn("预约提交失败,subscribeBO:{}", subscribeBO);
        return ResultUtil.fail().buildMessage("提交预约失败，请稍后再试");
    }

    @Operation(summary = "删除预约")
    @RequiresPermissions("subscribe:delete")
    @DeleteMapping("/{subscribeId}")
    public Result deleteSubscribe(@PathVariable Integer subscribeId) {

        boolean flag = iSubscribeService.update(new UpdateWrapper<Subscribe>().eq("id", subscribeId).set("status", StatusConstant.DELETE));
        if (flag)
            return ResultUtil.success().buildMessage("删除成功");
        log.warn("预约提删除失败,subscribeId:{}", subscribeId);
        return ResultUtil.fail().buildMessage("删除失败，请稍后再试");
    }

}
