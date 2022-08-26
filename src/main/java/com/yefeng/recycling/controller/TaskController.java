package com.yefeng.recycling.controller;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.yefeng.recycling.BO.TaskBO;
import com.yefeng.recycling.entity.Task;
import com.yefeng.recycling.result.Result;
import com.yefeng.recycling.result.ResultUtil;
import com.yefeng.recycling.service.ITaskService;
import com.yefeng.recycling.util.JwtUtil;
import com.yefeng.recycling.util.TokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yefeng
 * @since 2022-08-23
 */
@Tag(name = "task-controller", description = "任务接口")
@RestController
@RequestMapping("/task")
public class TaskController {

    private static final Log log = LogFactory.get();

    @Resource
    ITaskService iTaskService;

    @Operation(summary = "获取所有任务，需要管理员和超级管理员权限")
    @RequiresRoles(value = {"ROLE_ADMIN", "ROLE_ROOT"}, logical = Logical.OR)
    @GetMapping("/all")
    public Result getAllTask() {
        ArrayList<Task> tasks = iTaskService.getAllTask();
        return ResultUtil.success().buildData(tasks);
    }


    @Operation(summary = "根据员工id获取任务,必要要有task的所有权限")
    @RequiresPermissions(value = {"task:*"})
    @GetMapping("/{salesmanId}")
    public Result getTaskBySalesmanId(@Parameter(description = "员工id/用户id") @PathVariable Integer salesmanId) {
        ArrayList<Task> taskList = iTaskService.getTaskBySalesmanId(salesmanId);
        log.info(taskList.toString());
        return ResultUtil.success(taskList);
    }

    @Operation(summary = "根据任务id修改任务,必要要有task的update权限")
    @RequiresPermissions(value = {"task:update"})
    @PutMapping("/{taskId}")
    public Result updateTaskBySalesmanId(HttpServletRequest request, @Parameter(description = "任务id") @PathVariable Integer taskId, @Parameter(description = "task表单") @RequestBody TaskBO taskBO) {


        String token = TokenUtil.getToken(request);
        String userId = JwtUtil.getUserIdByToken(token);

        if (userId != null) {
            taskBO.setSubscribeId(null);//预约id不可更改
            Boolean flag = iTaskService.updateTask(Integer.valueOf(userId), taskBO);

            if (flag){
                return ResultUtil.success();
            }
        }
        return ResultUtil.fail().buildMessage("修改任务失败");
    }







}
