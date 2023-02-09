package com.yefeng.recycling.handller;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.yefeng.recycling.result.Result;
import com.yefeng.recycling.result.ResultUtil;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@RestControllerAdvice(basePackages = "com.hy.brush.lesson.admin.controller")
@RestControllerAdvice
public class MyExceptionHandler {

    private static final Log log= LogFactory.get();

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Result Exception(Exception e){
        log.error(e.getMessage());
        Result result = new Result();
        result.setCode(501);
        result.setMsg("发生系统错误，请稍后再试试");
        return result;
    }

    @ResponseBody
    @ExceptionHandler({AuthorizationException.class})
    public Result handle401(Exception e) {
        log.info("踩踩踩踩踩踩从");
        return ResultUtil.unAuthorized().buildMessage("您没有权限访问！");
    }




    @ResponseBody
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public Result MethodArgumentNotValidException(MethodArgumentNotValidException e)
    {
        log.error(e.getMessage());
        Result result = new Result();
        result.setCode(400);
        result.setMsg("参数错误"+e.getFieldError().getDefaultMessage());
        return result;
    }

    @ResponseBody
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result HttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e){
        log.error(e.getMessage());
        Result result = new Result();
        result.setCode(405);
        result.setMsg("不支持"+e.getMethod()+"方法");
        return result;
    }
    @ResponseBody
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public Result HttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e){
        log.error(e.getMessage());
        Result result = new Result();
        result.setCode(406);
        result.setMsg("不支持"+e.getContentType()+"格式");
        return result;
    }

    @ResponseBody
    @ExceptionHandler(FileSizeLimitExceededException.class)
    public Result FileSizeLimitExceededException(FileSizeLimitExceededException e){
        log.error(e.getMessage());
        Result result = new Result();
        result.setCode(405);
        result.setMsg("文件超过10MB");
        return result;
    }

    @ExceptionHandler({BindException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    protected Result handleBindException(BindException ex) {
        log.error("参数绑定失败", ex);
        List<Map<String, String>> list = new ArrayList<>();
        for (ObjectError objectError : ex.getAllErrors()) {
            Map<String, String> map = new HashMap<>();
            if (objectError instanceof FieldError) {
                FieldError fieldError = (FieldError) objectError;
                map.put("field", fieldError.getField());
                map.put("message", fieldError.getDefaultMessage());
            } else {
                map.put("field", objectError.getObjectName());
                map.put("message", objectError.getDefaultMessage());
            }
            list.add(map);
        }
        return ResultUtil.error().buildMessage("参数绑定失败").buildData(list);
    }

}
