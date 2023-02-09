package com.yefeng.recycling.service.impl;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yefeng.recycling.BO.TaskBO;
import com.yefeng.recycling.entity.Task;
import com.yefeng.recycling.mapper.TaskMapper;
import com.yefeng.recycling.service.ITaskService;
import com.yefeng.recycling.util.StatusConstant;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yefeng
 * @since 2022-08-23
 */
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements ITaskService {

    private static final Log log= LogFactory.get();
    @Resource
    TaskMapper taskMapper;

    @Override
    public ArrayList<Task> getAllTask() {
        List<Task> tasks = baseMapper.selectList(new QueryWrapper<>());
        return new ArrayList<>(tasks);
    }

    @Override
    public ArrayList<Task> getTaskBySalesmanId(Integer salesmanId) {
        List<Task> tasks = baseMapper.selectList(new QueryWrapper<Task>().eq("salesman_id", salesmanId));
        return new ArrayList<>(tasks);
    }


    @Override
    public Boolean updateTask(Integer updateId, TaskBO taskBO) {
        Task task = new Task();
        BeanUtils.copyProperties(taskBO, task);
        UpdateWrapper<Task> update_id = new UpdateWrapper<Task>().set("update_by", updateId);
        int update = baseMapper.update(task, update_id);
        return update > 0;

    }

    @Override
    public Boolean finishTask(Integer updateId, Integer taskId) {
        return changeTaskStatus(updateId, StatusConstant.FINISH, taskId);
    }

    @Override
    public Boolean invalidTask(Integer updateId, Integer taskId) {
        return changeTaskStatus(updateId, StatusConstant.INVALID, taskId);
    }

    @Override
    public Boolean runTask(Integer updateId, Integer taskId) {
        return changeTaskStatus(updateId, StatusConstant.RUN, taskId);
    }

    @Override
    public Boolean deleteTask(Integer updateId, Integer taskId) {
        return changeTaskStatus(updateId, StatusConstant.DELETE, taskId);
    }



    @Override
    public Boolean deleteTasks(Integer updateId, int... taskId) {

        UpdateWrapper<Task> taskUpdateWrapper = new UpdateWrapper<>();

        Object[] objects=new Object[taskId.length];

        for (int i = 0; i < taskId.length; i++) {
            objects[i]=taskId[i];
        }


        taskUpdateWrapper.set("update_by", updateId).set("status", StatusConstant.DELETE).in("id",objects);
        int update = baseMapper.update(null, taskUpdateWrapper);
        return update>0;

//        return changeTaskStatus(updateId, StatusConstant.DELETE, taskId);
    }


    @Override
    public Integer saveTask(TaskBO taskBO) {
        Task task = new Task();
        BeanUtils.copyProperties(taskBO,task);
        task.setStatus(StatusConstant.ACTIVE);
        Integer integer = taskMapper.insertTask(task);

        log.info("saveTask 返回值int {}",integer);
        log.info("task:{}",task);

       if (integer>0){
           return task.getId();
       }
       return -1;
    }

    private Boolean changeTaskStatus(Integer updateId, int status, Integer taskId) {

        UpdateWrapper<Task> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("update_by", updateId).set("status", status).eq("id", taskId);
//        Task task = new Task();
        int num = baseMapper.update(null, updateWrapper);
        return num > 0;
    }
}
