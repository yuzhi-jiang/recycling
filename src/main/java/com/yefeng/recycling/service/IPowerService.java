package com.yefeng.recycling.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yefeng.recycling.entity.Power;

import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yefeng
 * @since 2022-08-23
 */
public interface IPowerService extends IService<Power> {

     Set<Power> getPowerByRoleId(Integer roleId);



}
