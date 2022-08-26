package com.yefeng.recycling.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yefeng.recycling.entity.Type;
import com.yefeng.recycling.mapper.TypeMapper;
import com.yefeng.recycling.service.ITypeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yefeng
 * @since 2022-08-23
 */
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements ITypeService {

    @Override
    public ArrayList<Type> getAllTypeInfo() {
        return (ArrayList<Type>) baseMapper.selectList(new QueryWrapper<>());
    }
}
