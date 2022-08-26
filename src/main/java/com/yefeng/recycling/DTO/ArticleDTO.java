package com.yefeng.recycling.DTO;

import com.yefeng.recycling.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema
@Data
public class ArticleDTO extends BaseEntity {

    private Integer id;

    @ApiModelProperty("类型id")
    private Integer typeId;

    @ApiModelProperty("类型名")
    private String type;

    @ApiModelProperty("类型名")
    private String typeName;

    @ApiModelProperty("父类型id")
    private Integer parentId;


    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("状态")
    private Integer status;

}
