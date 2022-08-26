package com.yefeng.recycling.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yefeng.recycling.entity.Subscribe;
import com.yefeng.recycling.mapper.SubscribeMapper;
import com.yefeng.recycling.service.ISubscribeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yefeng
 * @since 2022-08-23
 */
@Service
public class SubscribeServiceImpl extends ServiceImpl<SubscribeMapper, Subscribe> implements ISubscribeService {

    @Override
    public ArrayList<Subscribe> getAllSubscribe() {
        List<Subscribe> subscribes = baseMapper.selectAllSubscribe();
        return new ArrayList<>(subscribes);
    }

    @Override
    public ArrayList<Subscribe> getSubscribe(Integer userId) {
        List<Subscribe> list = baseMapper.selectList(new QueryWrapper<Subscribe>().eq("user_id", userId));

        return new ArrayList<>(list);
    }
}
