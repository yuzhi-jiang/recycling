package com.yefeng.recycling.controller;

import com.yefeng.recycling.VO.RolePowerVO;
import com.yefeng.recycling.VO.UserRoleVO;
import com.yefeng.recycling.entity.Power;
import com.yefeng.recycling.request.RequestParams;
import com.yefeng.recycling.result.Result;
import com.yefeng.recycling.result.ResultUtil;
import com.yefeng.recycling.service.IPowerService;
import com.yefeng.recycling.service.IUserService;
import com.yefeng.recycling.service.UserDetailService;
import com.yefeng.recycling.util.JwtUtil;
import com.yefeng.recycling.util.TokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yefeng
 * @since 2022-08-23
 */
@Tag(name = "user-controller", description = "用户模块")
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    IUserService iUserService;

    @Operation(summary = "用户登录")
    @PostMapping("/login")
    @Parameters({
            @Parameter(name = "user", description = "用户名:userName,密码：password"),
    })
    public Result login(@RequestBody RequestParams user) throws BindException {
        String userName = user.getStringValue("userName");
        String password = user.getStringValue("password");
        return iUserService.login(userName, password);
    }


    @Resource
    UserDetailService userDetailService;

    @Resource
    IPowerService iPowerService;

    @Operation(summary = "获取用户信息")
    @GetMapping("/userInfo")
    public Result getUserInfo(HttpServletRequest request) {
        String token = TokenUtil.getToken(request);
        if (token == null) {
            return ResultUtil.fail().buildMessage("请先登录");
        }

        String userName = JwtUtil.getUserName(token);
        UserRoleVO userDetail = userDetailService.getUserDetail(userName);

        Set<RolePowerVO> roles = userDetail.getRoles();

        roles.forEach(rolePowerVO -> {
            Set<Power> powerByRoleId = iPowerService.getPowerByRoleId(rolePowerVO.getId());
            rolePowerVO.setPowers(powerByRoleId);
        });


        if (userDetail == null) {
            return ResultUtil.fail().buildMessage("无法获取用户信息，请稍后再试");
        }
        return ResultUtil.success(userDetail);
    }
}
