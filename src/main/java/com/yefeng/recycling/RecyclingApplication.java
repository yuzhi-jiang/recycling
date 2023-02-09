package com.yefeng.recycling;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

@SpringBootApplication
@MapperScan({"com.baomidou.mybatisplus.samples.quickstart.mapper","com.yefeng.recycling.mapper"})
@EnableOpenApi
@EnableKnife4j
public class RecyclingApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecyclingApplication.class, args);
    }

}


