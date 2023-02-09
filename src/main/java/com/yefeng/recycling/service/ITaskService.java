package com.yefeng.recycling.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yefeng.recycling.BO.TaskBO;
import com.yefeng.recycling.entity.Task;

import java.util.ArrayList;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yefeng
 * @since 2022-08-23
 */
public interface ITaskService extends IService<Task> {

    ArrayList<Task> getAllTask();


    ArrayList<Task> getTaskBySalesmanId(Integer salesmanId);


    Boolean updateTask(Integer updateId, TaskBO taskBO);


    Boolean invalidTask(Integer updateId, Integer taskId);

    Boolean finishTask(Integer updateId, Integer taskId);

    Boolean runTask(Integer updateId, Integer taskId);

    Boolean deleteTask(Integer updateId, Integer taskId);

    Boolean deleteTasks(Integer updateId, int... taskId);

    Integer saveTask(TaskBO taskBO);
}
