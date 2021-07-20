package com.zmgab.springbootshiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("user")
public class UserController {
    /**
     * 用来处理身份
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("user")
    public String login(String username, String password) {
        // 获取主体对象
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken authenticationToken = new UsernamePasswordToken(username,password);
        try {
            subject.login(authenticationToken);
            System.out.println("登录成功！");
            return "redirect:/index.jsp";
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            System.out.println("认证失败:用户名不存在");
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            System.out.println("认证失败: 认证信息不对,密码错误");
        } catch (Exception e) {
            System.out.println("认证失败");
        }

        return "redirect:/login.jsp";
    }
}
