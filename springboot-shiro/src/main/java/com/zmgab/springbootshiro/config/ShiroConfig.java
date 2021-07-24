package com.zmgab.springbootshiro.config;

import com.zmgab.springbootshiro.shiro.CustomerRealm;
import com.zmgab .springbootshiro.shiro.RedisCacheManager;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
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
        map.put("/user/login", "anon"); // anon 设置为公共资源 放行资源放在下面
        map.put("/user/register", "anon"); // 设置为公共资源 放行资源放在下面
        map.put("/user/getImage", "anon"); // 设置为公共资源 放行生成验证码
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
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        hashedCredentialsMatcher.setHashIterations(1024);
        customerRealm.setCredentialsMatcher(hashedCredentialsMatcher);

        // 开启缓存管理
//        customerRealm.setCacheManager(new EhCacheManager());
        customerRealm.setCacheManager(new RedisCacheManager());
        customerRealm.setCachingEnabled(true); // 开启全局缓存
        customerRealm.setAuthenticationCachingEnabled(true); // 开启认证缓存
        customerRealm.setAuthenticationCacheName("authenticationCache");
        customerRealm.setAuthorizationCachingEnabled(true); // 开启授权缓存
        customerRealm.setAuthorizationCacheName("authorizationCache");

        return customerRealm;
    }
}