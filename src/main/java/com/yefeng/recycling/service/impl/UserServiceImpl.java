package com.yefeng.recycling.service.impl;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yefeng.recycling.DTO.RoleDTO;
import com.yefeng.recycling.DTO.UserDTO;
import com.yefeng.recycling.VO.UserVO;
import com.yefeng.recycling.entity.User;
import com.yefeng.recycling.mapper.UserMapper;
import com.yefeng.recycling.result.Result;
import com.yefeng.recycling.result.ResultUtil;
import com.yefeng.recycling.service.IUserService;
import com.yefeng.recycling.service.UserDetailService;
import com.yefeng.recycling.util.JwtUtil;
import com.yefeng.recycling.util.UserUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Set;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yefeng
 * @since 2022-08-23
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    private static final Log log = LogFactory.get();
    @Resource
    UserDetailService userDetailService;

    @Override
    @Transactional
    public Result login(String userName, String password) {
        UserDTO userInfo = getUser(userName);

        String encryptPwd = UserUtil.getUserEncryptPassword(userName, password);
        if (userInfo == null) {
            return ResultUtil.fail().buildMessage("登录失败，用户不存在！");
        }

        if (!encryptPwd.equals(userInfo.getPassword())) {
            return ResultUtil.fail().buildMessage("登录失败，密码输入错误！");
        }

        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userInfo, userVO);
        //获取用户的角色
        Set<RoleDTO> userRoleSet = userDetailService.getUserRoleByUserId(userInfo.getId());
//        登录完成返回数据/token

        String token = JwtUtil.createToken(
                String.valueOf(userInfo.getId()),
                userInfo.getUserName(),
//                userVO,
                userRoleSet.stream().map(RoleDTO::getRoleKey).toArray(String[]::new));
        log.warn(token);
        System.out.println(token);
        return ResultUtil.success().buildData(token).buildMessage("登录成功");
    }

    @Override
    public String getToken(Integer userId, String userName) {
        Set<RoleDTO> userRoleSet = userDetailService.getUserRoleByUserId(userId);
//        登录完成返回数据/token

        String token = JwtUtil.createToken(
                String.valueOf(userId),
                userName,
//                userVO,
                userRoleSet.stream().map(RoleDTO::getRoleKey).toArray(String[]::new));

        log.info(token);
        System.out.println(token);
        return token;
    }


    @Resource
    UserMapper userMapper;

    @Override
    public Boolean hadUser(String userName) {
        Integer count = userMapper.hadUser(userName);
        return count != null;
    }

    public UserDTO getUser(String userName) {
        User user = baseMapper.selectOne(new QueryWrapper<User>().eq("user_name", userName));
        if (user == null) return null;
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        return userDTO;
    }

}
