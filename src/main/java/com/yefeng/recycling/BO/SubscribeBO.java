package com.yefeng.recycling.BO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class SubscribeBO implements Serializable {

    @ApiModelProperty("主键,预约成功后返回")
    private Integer id;

    @ApiModelProperty("预约者电话")
    private String phone;

    @ApiModelProperty("预约者地址名称")
    private String address;

    @ApiModelProperty("预约者经纬度")
    private String addressPoint;

    @ApiModelProperty("预约上门时间")
    private LocalDateTime scheduledTime;

    @ApiModelProperty("用户id，如果有的话")
    private Integer userId;
}
