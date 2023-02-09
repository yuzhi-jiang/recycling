package com.yefeng.recycling.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yefeng.recycling.entity.Role;

import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yefeng
 * @since 2022-08-23
 */
public interface IRoleService extends IService<Role> {

     Set<Role> getRoleById(Integer roleId);


}
