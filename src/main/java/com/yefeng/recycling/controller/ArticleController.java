package com.yefeng.recycling.controller;

import com.yefeng.recycling.DTO.ArticleDTO;
import com.yefeng.recycling.result.Result;
import com.yefeng.recycling.result.ResultUtil;
import com.yefeng.recycling.service.IArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yefeng
 * @since 2022-08-23
 */
@Tag(name = "article-controller",description = "文章/公告/广告等信息数据--查询接口")
@RestController
@RequestMapping("/article")
public class ArticleController {


    @Resource
    IArticleService iArticleService;

    @GetMapping("/getNotices")
    @Operation(summary = "根据信息类型获取数据")
    public Result getNotices(@Parameter(description = "类型id") Integer typeId) {

        ArrayList<ArticleDTO> dtoArrayList = iArticleService.getNotices(typeId);
        if (dtoArrayList == null || dtoArrayList.size() == 0) {
            return ResultUtil.fail().buildMessage("获取信息失败,请稍后再试");
        }
        return ResultUtil.success().buildData(dtoArrayList);
    }

}

