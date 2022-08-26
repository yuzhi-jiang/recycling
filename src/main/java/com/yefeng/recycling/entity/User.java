package com.yefeng.recycling.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author yefeng
 * @since 2022-08-23
 */
@ApiModel(value = "User对象", description = "")
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("电话")
    private String phone;

    @ApiModelProperty("头像")
    private String icon;

    @ApiModelProperty("邮箱")
    private String email;



    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("状态,激活为1,停用:0,封号:2")
    private Integer status;

    @ApiModelProperty("是否删除 ,1为删除，0为正常")
    private Boolean isDelete;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("创建人")
    private Integer createBy;

    @ApiModelProperty("更新人")
    private Integer updateBy;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

}
