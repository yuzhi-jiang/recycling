package com.yefeng.recycling.BO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

@Data
public class WxUserBO implements Serializable {

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("昵称")
    @NotNull("昵称不能位空")
    private String nickname;

    @ApiModelProperty("电话")
    private String phone;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("头像")
    @NonNull
    private String icon;

    @ApiModelProperty("邮箱")
    private String email;


}
