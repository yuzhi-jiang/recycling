package com.yefeng.recycling;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.yefeng.recycling.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;

@SpringBootTest
class RecyclingApplicationTests {

    private static final Log log = LogFactory.get();

    @Test
    void getUserName(){
        String userName = RandomUtil.randomString(10);
        System.out.println(userName);
    }


    @Resource
    UserMapper userMapper;
    @Test
    void hadUser(){
        Integer count1 = userMapper.hadUser("yefeng");
        Integer count2 = userMapper.hadUser("yefeng1");
        System.out.println(count1);
        System.out.println(count2);
    }

    @Test
    void contextLoads() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("1",2);
        map.put("3",4);
        String token = JWTUtil.createToken(map, "123".getBytes());

        System.out.println("token:"+token);


        JWT jwt = JWTUtil.parseToken(token);
        System.out.println(jwt.getHeader());
        System.out.println(jwt.getPayloads());
        System.out.println(jwt.getSigner());


        boolean verify = JWTUtil.verify(token, "123".getBytes());
        System.out.println("verify"+verify);

        boolean validate = JWT.of(token).setKey("231".getBytes()).validate(0);

        System.out.println("validate:"+validate);
    }

    @Test
    public void generator(){

        String tables="article," +
                "dictionary," +
                "order," +
                "order_item," +
                "power," +
                "role," +
                "role_power," +
                "subscribe," +
                "task," +
                "type," +
                "user," +
                "user_role";

        FastAutoGenerator.create("jdbc:mysql://localhost:3306/ssr", "root", "jiangshao888")
                .globalConfig(builder -> {
                    builder.author("yefeng") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件

                            .outputDir("F:\\Projects\\IDEAproject\\recycling\\src\\main\\java\\com\\yefeng\\recycling"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.baomidou.mybatisplus.samples.generator") // 设置父包名
                            .moduleName("recycling") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "F:\\Projects\\IDEAproject\\recycling\\src\\main\\resources\\mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude(tables); // 设置需要生成的表名
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();

    }

}
