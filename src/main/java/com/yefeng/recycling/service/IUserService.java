package com.yefeng.recycling.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yefeng.recycling.entity.User;
import com.yefeng.recycling.result.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yefeng
 * @since 2022-08-23
 */
public interface IUserService extends IService<User> {
    Result login(String userName, String password);
    String getToken(Integer userId,String userName);


    Boolean hadUser(String userName);

}
