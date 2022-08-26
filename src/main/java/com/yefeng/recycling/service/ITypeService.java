package com.yefeng.recycling.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yefeng.recycling.entity.Type;

import java.util.ArrayList;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yefeng
 * @since 2022-08-23
 */
public interface ITypeService extends IService<Type> {

    ArrayList<Type> getAllTypeInfo();
}
