package com.yefeng.recycling.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yefeng.recycling.entity.Role;
import com.yefeng.recycling.mapper.RoleMapper;
import com.yefeng.recycling.service.IRoleService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yefeng
 * @since 2022-08-23
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {


    @Override
    public Set<Role> getRoleById(Integer roleId) {
        List<Role> roles = baseMapper.selectList(new QueryWrapper<Role>().eq("id", roleId));
        return new HashSet<>(roles);
    }
}
