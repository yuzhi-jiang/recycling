package com.yefeng.recycling.VO;

import com.yefeng.recycling.entity.Power;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class UserRoleVO implements Serializable {
    private static final long serialVersionUID = 216512311L;


//    @ApiModelProperty("用户id")
////    @TableId(value = "id", type = IdType.AUTO)
//    private Integer id;
//
//    @ApiModelProperty("用户名")
//    private String userName;
//
//    @ApiModelProperty("昵称")
//    private String nickname;
//
//    @ApiModelProperty("电话")
//    private String phone;
//
//    @ApiModelProperty("地址")
//    private String address;
//
//    @ApiModelProperty("头像")
//    private String icon;
//
//    @ApiModelProperty("邮箱")
//    private String email;

    @ApiModelProperty("用户信息")
    UserVO userInfo;

    @ApiModelProperty("角色")
    Set<RolePowerVO> roles;

    public Set<RolePowerVO> getRoles() {
        return roles;
    }

    public List<String> getAllPower() {
        ArrayList<String> list = new ArrayList<>();
        if (roles.size() > 0) {
            HashSet<String> set = new HashSet<>();
            roles.forEach(rolePowerVO -> {
                List<String> collect = rolePowerVO.getPowers().stream().map(Power::getPower).collect(Collectors.toList());
                set.addAll(collect);
            });


            list = new ArrayList<>(set);

        }
        return list;
    }

    public UserRoleVO() {

    }
}
