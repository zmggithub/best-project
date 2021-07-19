package com.zmgab.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;

public class Authenticator {


    public static void main(String[] args) {
        // 1.创建安全管理器对象
        DefaultSecurityManager securityManager = new DefaultSecurityManager();

        // 2.给安全管理器设置realm
        securityManager.setRealm(new IniRealm("classpath:shiro.ini"));

        // 3.SecurityUtils  全局安全工具类
        SecurityUtils.setSecurityManager(securityManager);

        // 4.关键对象,当前登录的主体对象
        Subject subject = SecurityUtils.getSubject();

        // 5.创建令牌
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("zhangsan", "123");

        // 用户认证
        try {
            System.out.println("认证状态 : " + subject.isAuthenticated());
            subject.login(usernamePasswordToken);
            System.out.println("认证状态 : " + subject.isAuthenticated());
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            System.out.println("认证失败:用户名不存在");
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            System.out.println("认证失败: 认证信息不对(比如,密码错误)");
        } catch (Exception e) {
            System.out.println("认证失败");
        }


    }
}
