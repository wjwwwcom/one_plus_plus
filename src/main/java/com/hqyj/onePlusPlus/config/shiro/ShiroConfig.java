package com.hqyj.onePlusPlus.config.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * author  Jayoung
 * createDate  2020/9/10 0010 9:08
 * version 1.0
 */
@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();

        factoryBean.setSecurityManager(securityManager);

        HashMap<String, String> map = new HashMap<>();
        /*
        *  authc 代表必须要认证后才能访问
        *  anon 代表不需要认证就可以访问
        * */
//        map.put("/user/login","anon");
//        map.put("/user/toLogin","anon");
//        map.put("/user/register","anon");
//        map.put("/user/toRegister","anon");
//        map.put("/user/isUserNameUsable","anon");
//        map.put("/user/active/**","anon");
//        map.put("/css/**","anon");
//        map.put("/js/**","anon");
        map.put("/**", "anon");

        factoryBean.setFilterChainDefinitionMap(map);
        //factoryBean.setLoginUrl("/account/user/login");
        return factoryBean;
    }

    @Bean
    // @Qualifier 根据name进行注入，ioc容器中会管理多个Realm，如果使用类型注入，可能导致注入的不是自己想要的Realm
    public DefaultWebSecurityManager getSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    @Bean(name = "userRealm")
    public UserRealm getUserRealm(){
        UserRealm userRealm = new UserRealm();

        //指明加密的次数、名字等参数
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashIterations(1024);
        matcher.setHashAlgorithmName("md5");

        userRealm.setCredentialsMatcher(matcher);

        return userRealm;
    }

    /*
     *  注册shiro方言，让Thymeleaf支持shiro标签
     * */
    @Bean
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }

}
