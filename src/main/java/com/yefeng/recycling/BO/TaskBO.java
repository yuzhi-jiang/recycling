package com.yefeng.recycling.BO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel(value="TaskBO对象", description="组件")
public class TaskBO implements Serializable {
    private static final long serialVersionUID = 1231L;



    @ApiModelProperty("主键")
    private Integer id;

    @ApiModelProperty("预约id")
    private Integer subscribeId;

    @ApiModelProperty("上门时间")
    private LocalDateTime callTime;

    @ApiModelProperty("业务员")
    private String salesman;

    @ApiModelProperty("业务员电话")
    private String salesmanPhone;

    @ApiModelProperty("业务员id")
    private String salesmanId;

//    @ApiModelProperty("状态")
//    private Integer status;
}
