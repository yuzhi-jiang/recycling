package com.yefeng.recycling.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yefeng.recycling.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yefeng
 * @since 2022-08-23
 */
public interface UserMapper extends BaseMapper<User> {

    @Select("select 1 from user where user_name=#{userName}")
    Integer hadUser(@Param("userName") String userName);
}
