package com.yefeng.recycling.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yefeng.recycling.entity.OrderItem;
import com.yefeng.recycling.mapper.OrderItemMapper;
import com.yefeng.recycling.service.IOrderItemService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yefeng
 * @since 2022-08-23
 */
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements IOrderItemService {

}
