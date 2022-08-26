package com.yefeng.recycling.mapper;

import com.yefeng.recycling.entity.Role;
import com.yefeng.recycling.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

public interface UserDetailMapper {


    @Select("select * from user where user_name=#{userName}")
    User findUser(@Param("userName") String userName);


    @Select("select r.id,role_key,role_value,r.status from user_role as ur join role r on r.id = ur.role_id\n" +
            "where ur.status!=0 and user_id=#{userId}")
    Set<Role> findRoleByUserId(Integer userId);
}
