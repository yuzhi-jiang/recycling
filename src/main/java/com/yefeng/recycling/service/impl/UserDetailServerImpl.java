package com.yefeng.recycling.service.impl;

import com.yefeng.recycling.DTO.RoleDTO;
import com.yefeng.recycling.DTO.UserDTO;
import com.yefeng.recycling.VO.RolePowerVO;
import com.yefeng.recycling.VO.UserRoleVO;
import com.yefeng.recycling.VO.UserVO;
import com.yefeng.recycling.entity.Role;
import com.yefeng.recycling.entity.User;
import com.yefeng.recycling.mapper.UserDetailMapper;
import com.yefeng.recycling.service.UserDetailService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailServerImpl implements UserDetailService {

    @Resource
    UserDetailMapper userDetailMapper;


    @Transactional
    @Override
    public UserRoleVO getUserDetail(String userName) {
        UserDTO userInfo = getUserInfo(userName);
        if (userInfo==null){
            return null;
        }


        Set<RoleDTO> rolePowerDTO = this.getUserRoleByUserId(userInfo.getId());

        UserVO userVO = new UserVO();
        Set<RolePowerVO> rolePowerVOS = new HashSet<>();

        //拷贝属性
        BeanUtils.copyProperties(userInfo, userVO);

        //stream流的方式
        rolePowerVOS = rolePowerDTO.stream()
                .map(rolePower -> {
                    RolePowerVO rolePowerVO = new RolePowerVO();
                    BeanUtils.copyProperties(rolePower, rolePowerVO);
                    return rolePowerVO;
                })
                .collect(Collectors.toSet());


        UserRoleVO userRoleVO = new UserRoleVO();


        userRoleVO.setUserInfo(userVO);
        userRoleVO.setRoles(rolePowerVOS);

        return userRoleVO;
    }

    public Set<RoleDTO> getUserRoleByUserId(Integer userId) {
        Set<Role> roles = userDetailMapper.findRoleByUserId(userId);
        return roles.stream().map(role -> {
            RoleDTO dto = new RoleDTO();
            BeanUtils.copyProperties(role, dto);
            return dto;
        }).collect(Collectors.toSet());
    }


    private UserDTO getUserInfo(String userName) {
        User user = userDetailMapper.findUser(userName);
        if (user==null)return null;
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        return userDTO;
    }


}
