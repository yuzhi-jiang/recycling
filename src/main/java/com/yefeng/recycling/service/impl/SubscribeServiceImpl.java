package com.yefeng.recycling.service.impl;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yefeng.recycling.BO.SubscribeBO;
import com.yefeng.recycling.entity.Subscribe;
import com.yefeng.recycling.mapper.SubscribeMapper;
import com.yefeng.recycling.service.ISubscribeService;
import com.yefeng.recycling.util.StatusConstant;
import org.springframework.beans.BeanUtils;
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

    private static final Log log= LogFactory.get();
    @Override
    public boolean saveSubscribe(SubscribeBO subscribeBO) {
        log.info("subscribeBO:{}",subscribeBO);
        Subscribe subscribe = new Subscribe();
        subscribe.setStatus(StatusConstant.ACTIVE);
        BeanUtils.copyProperties(subscribeBO,subscribe);
        Integer integer = baseMapper.insertSubscribe(subscribe);
        return integer>0;
    }

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
