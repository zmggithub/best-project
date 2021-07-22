package com.zmgab.springbootshiro.config;

import com.zmgab.springbootshiro.shiro.CustomerRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 用来整合shiro框架相关的配置类
 */
@Configuration
public class ShiroConfig {

    // 1.创建shiroFilter 负责拦截所有请求
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        // 给filter设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);

        // 配置系统受限资源
        // 配置系统公共资源
        Map<String, String> map = new HashMap<>();
        map.put("/user/login", "anon"); // anon 设置为公共资源
        map.put("/user/register", "anon");
        map.put("/register.jsp", "anon");

//        map.put("/index.jsp", "authc"); //authc 请求这个资源需要认证和授权

        // 配置通配符
          map.put("/**", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);

        // 默认认证界面路径 代码中默认是会访问login.jsp，对jsp友好。
        shiroFilterFactoryBean.setLoginUrl("/login.jsp");

        return shiroFilterFactoryBean;
    }


    // 2.创建安全管理器
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("getRealm") Realm realm) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();

        defaultWebSecurityManager.setRealm(realm);
        return defaultWebSecurityManager;
    }

    // 3.创建自定义realm
    @Bean
    public Realm getRealm() {
        CustomerRealm customerRealm = new CustomerRealm();
        return customerRealm;
    }
}