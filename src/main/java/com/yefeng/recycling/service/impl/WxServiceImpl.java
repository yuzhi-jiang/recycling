package com.yefeng.recycling.service.impl;

import com.yefeng.recycling.JWT.AccountProfile;
import com.yefeng.recycling.mapper.WxMapper;
import com.yefeng.recycling.service.WxService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class WxServiceImpl implements WxService {

    @Resource
    WxMapper wxMapper;

    @Override
    public AccountProfile getUserByOpenId(String openId) {
      return  wxMapper.findUserByOpenId(openId);
    }
}
