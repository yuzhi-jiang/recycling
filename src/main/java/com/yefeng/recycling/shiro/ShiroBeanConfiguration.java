package com.yefeng.recycling.shiro;

import org.apache.shiro.event.EventBus;
import org.apache.shiro.spring.ShiroEventBusBeanPostProcessor;
import org.apache.shiro.spring.config.AbstractShiroBeanConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroBeanConfiguration extends AbstractShiroBeanConfiguration {
//    @Bean
//    @Override
//    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
//        return super.lifecycleBeanPostProcessor();
//    }

    @Bean
    @Override
    protected EventBus eventBus() {
        return super.eventBus();
    }

    @Bean
    @Override
    public ShiroEventBusBeanPostProcessor shiroEventBusAwareBeanPostProcessor() {
        return super.shiroEventBusAwareBeanPostProcessor();
    }
}