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
@ApiModel(value = "Subscribe对象", description = "")
@Data
public class Subscribe implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("预约者电话")
    private String phone;

    @ApiModelProperty("预约者地址名称")
    private String address;

    @ApiModelProperty("预约者经纬度")
    private String  addressPoint;

    @ApiModelProperty("预约上门时间")
    private LocalDateTime scheduledTime;

    @ApiModelProperty("用户id，如果有的话")
    private Integer userId;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("创建人")
    private Integer createBy;

    @ApiModelProperty("更新人")
    private Integer updateBy;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("状态,1有效,2进行中,3,完成,0,失效")
    private Integer status;

}
