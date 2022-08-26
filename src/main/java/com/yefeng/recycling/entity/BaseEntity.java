package com.yefeng.recycling.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseEntity {
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("创建人")
    private Integer createBy;

    @ApiModelProperty("更新人")
    private Integer updateBy;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;
}
