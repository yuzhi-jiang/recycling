package com.yefeng.recycling.publicController;

import com.github.xiaoymin.knife4j.annotations.Ignore;
import com.yefeng.recycling.result.Result;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yefeng
 * @version 1.0.0
 * @ClassName ErrorController.java
 * @Description TODO
 * @createTime 2022年05月04日 23:46:00
 */
@Ignore
@RestController
@RequestMapping("/rbacManager")
public class ErrorController {

     @RequestMapping("/401/{msg}")
    public Result test(@PathVariable("msg") String msg){
         if (msg.isBlank()){
             throw new RuntimeException("msg is null");
         }
         Result result = new Result();
         result.setMsg(msg);
         result.setCode(401);
         return result;
     }
}
