package com.yefeng.recycling.controller;

import com.yefeng.recycling.entity.Type;
import com.yefeng.recycling.result.Result;
import com.yefeng.recycling.result.ResultUtil;
import com.yefeng.recycling.service.ITypeService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yefeng
 * @since 2022-08-23
 */
@RestController
@RequestMapping("/type")
public class TypeController {

    @Resource
    ITypeService iTypeService;

    @Operation(summary = "获取所有类型数据")
    @GetMapping("/getAllType")
    public Result getAllTypeInfo(){
       ArrayList<Type> typeArrayList= iTypeService.getAllTypeInfo();
       return ResultUtil.success().buildData(typeArrayList);
    }
}
