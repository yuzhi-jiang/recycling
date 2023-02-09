package com.yefeng.recycling.JWT;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Data
@Configuration
@ConfigurationProperties(prefix = "custom.jwt")
public class JwtProperties {
    /**
     * 盐
     */
    private String secret;
    /**
     * token 过期时间，单位：秒
     */

    private long expire;
    /**
     * token刷新时间，单位：天
     */
    private Integer refreshExpire;

}