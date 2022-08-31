package com.yefeng.recycling.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
//@ConfigurationProperties(prefix = "system.properties")
public class SystemPropertiesConfig {

    /**
     * 请求白名单
     */
    @Value("${system.properties.whitelist}")
    private String whitelist;


    @Value("${system.properties.imgSavePath}")
    private String IMAGE_SAVE_PATH;

}