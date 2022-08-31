package com.yefeng.recycling.config;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.function.Predicate;

@Configuration
@EnableOpenApi
public class SwaggerConfig {

    //    @Value("${spring.profiles.active:NA}")
//    private String active;
// 定义分隔符
    private static final String splitor = ";";
    @Value("${swagger.switch}")
    private boolean swaggerSwitch;
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)    //  OAS_30
                .enable(swaggerSwitch)    //  仅在开发环境开启Swagger
                .apiInfo(apiInfo())
//                .host("http://www.example.com")    //  Base  URL
                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.yefeng.recycling.controller"))
                .apis(basePackage("com.yefeng.recycling.controller"+splitor+"com.yefeng.recycling.publicController"))
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


    public static Predicate<RequestHandler> basePackage(final String basePackage) {
        return input -> declaringClass(input).transform(handlerPackage(basePackage)).or(true);
    }


    private static Function<Class<?>, Boolean> handlerPackage(final String basePackage) {
        return input -> {
            // 循环判断匹配
            for (String strPackage : basePackage.split(splitor)) {
                boolean isMatch = input.getPackage().getName().startsWith(strPackage);
                if (isMatch) {
                    return true;
                }
            }
            return false;
        };
    }

    private static Optional<? extends Class<?>> declaringClass(RequestHandler input) {
        return Optional.fromNullable(input.declaringClass());
    }
}

