package com.yefeng.recycling.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yefeng.recycling.entity.Power;
import com.yefeng.recycling.mapper.PowerMapper;
import com.yefeng.recycling.service.IPowerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
public class PowerServiceImpl extends ServiceImpl<PowerMapper, Power> implements IPowerService {

    @Resource
    PowerMapper powerMapper;

    @Override
    public Set<Power> getPowerByRoleId(Integer roleId) {
        List<Power> powers= powerMapper.findPowerByRoleId(roleId);
        return new HashSet<>(powers);
    }
}
