package com.yefeng.recycling.service;

import com.yefeng.recycling.JWT.AccountProfile;

public interface WxService {


    AccountProfile getUserByOpenId(String openId);

}
