package com.yefeng.recycling.config;


import com.yefeng.recycling.JWT.JWTFilter;
import com.yefeng.recycling.JWT.JWTRealm;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.util.StringUtils;

import javax.servlet.Filter;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Configuration
public class ShiroConfig {
    //    @Bean
//    ShiroRealm myRealm() {
//
//        return new ShiroRealm();
//    }
    @Autowired
    private SystemPropertiesConfig systemPropertiesConfig;//白名单

    private Set<String> getWhiteList() {
        String whitelists = this.systemPropertiesConfig.getWhitelist();
        if (!StringUtils.hasText(whitelists)) {
            return new HashSet<>();
        }
        Set<String> whiteList = new HashSet<>();
        String[] whiteArray = whitelists.split(",");
        for (int i = 0; i < whiteArray.length; i++) {
            String str = whiteArray[i];
            whiteList.add(whiteArray[i]);
        }
        return whiteList;
    }

    @Bean
    ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        chainDefinition.addPathDefinition("/user/login", "anon");
        chainDefinition.addPathDefinition("/test/*", "anon");
        chainDefinition.addPathDefinition("/swagger-ui/**", "anon");
        chainDefinition.addPathDefinition("/swagger*/**", "anon");
        chainDefinition.addPathDefinition("/v3/**", "anon");
        chainDefinition.addPathDefinition("/wx/**", "anon");
        chainDefinition.addPathDefinition("/unauthorized", "anon");
        chainDefinition.addPathDefinition("/rbacManager/*", "anon");

//        WhiteList.ALL.forEach(str -> {
//            chainDefinition.addPathDefinition(str, "anon");
//        });


        Set<String> whiteList = this.getWhiteList();        // 白名单Path

        whiteList.forEach(str->{
            chainDefinition.addPathDefinition(str, "anon");
        });

//其余的都走jwt自定义过滤器

//        chainDefinition.addPathDefinition("/**", "authc");
        chainDefinition.addPathDefinition("/**", "jwt");
        return chainDefinition;


    }

    /**
     * 设置过滤器，将自定义的Filter加入。
     */
    @Bean("shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);
        Map<String, Filter> filterMap = factoryBean.getFilters();
        filterMap.put("jwt", new JWTFilter());
        factoryBean.setFilters(filterMap);
        factoryBean.setFilterChainDefinitionMap(shiroFilterChainDefinition().getFilterChainMap());

        return factoryBean;
    }

    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        // 关闭shiro自带的session。这样不能通过session登录shiro，后面将采用jwt凭证登录。
        // 见：http://shiro.apache.org/session-management.html#SessionManagement-DisablingSubjectStateSessionStorage
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
//        securityManager().setCacheManager(getDatabaseRealm().getCacheManager());
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(getDatabaseRealm());
        securityManager.setSubjectDAO(subjectDAO);
        return securityManager;
    }

    /**
     * 开启shiro 注解。比如：@RequiresRole
     * 本处不用此方法开启注解，使用引入spring aop依赖的方式。原因见：application.yml里的注释
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
            SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor =
                new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    public JWTRealm getDatabaseRealm() {
        return new JWTRealm();
    }

    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public static DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }

    @Bean(name = "lifecycleBeanPostProcessor")
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }


}
