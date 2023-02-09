package com.yefeng.recycling.VO;

import com.yefeng.recycling.entity.Power;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
public class RolePowerVO implements Serializable {
    Integer id;
    String roleKey;
    String roleValue;



    @ApiModelProperty("权限集合")
    Set<Power> powers;

    public RolePowerVO() {

    }
}
