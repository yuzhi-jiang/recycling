package com.yefeng.recycling.service;

import com.yefeng.recycling.DTO.RoleDTO;
import com.yefeng.recycling.VO.UserRoleVO;

import java.util.Set;

public interface UserDetailService {



    UserRoleVO getUserDetail(String userName);

    Set<RoleDTO> getUserRoleByUserId(Integer userId);

}
