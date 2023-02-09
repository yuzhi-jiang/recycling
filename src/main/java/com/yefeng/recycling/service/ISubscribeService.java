package com.yefeng.recycling.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yefeng.recycling.BO.SubscribeBO;
import com.yefeng.recycling.entity.Subscribe;

import java.util.ArrayList;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yefeng
 * @since 2022-08-23
 */
public interface ISubscribeService extends IService<Subscribe> {
    ArrayList<Subscribe> getAllSubscribe();


    ArrayList<Subscribe> getSubscribe(Integer userId);

    boolean saveSubscribe(SubscribeBO subscribeBO);
}
