package com.yefeng.recycling.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@EnableOpenApi
public class SwaggerConfig {

//    @Value("${spring.profiles.active:NA}")
//    private String active;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)    //  OAS_30
                .enable(true)    //  仅在开发环境开启Swagger
                .apiInfo(apiInfo())
//                .host("http://www.example.com")    //  Base  URL
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.yefeng.recycling.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("API文档")
                .description("这是描述信息")
                .contact(new Contact("叶枫", null, null))
                .version("1.0")
                .build();
    }
}

