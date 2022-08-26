package com.yefeng.recycling.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@TableName("v_article")
@Data
public class VArticlePO extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 231L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("类型id")
    private Integer typeId;

    @ApiModelProperty("类型名")
    private String type;

    @ApiModelProperty("父类型id")
    private Integer parentId;

    @ApiModelProperty("类型名")
    private String typeName;


    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("状态")
    private Integer status;
}
