package com.yefeng.recycling.mapper;


import com.yefeng.recycling.JWT.AccountProfile;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface WxMapper {
    @Select("select user.id,user_name from wx_user as wu join user on user.id=wu.user_id where wu.openid=#{openid}")
    AccountProfile findUserByOpenId(@Param("openid") String openId);
}
