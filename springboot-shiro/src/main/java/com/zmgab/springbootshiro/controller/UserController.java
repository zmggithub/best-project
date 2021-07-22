package com.zmgab.springbootshiro.controller;

import com.zmgab.springbootshiro.entity.User;
import com.zmgab.springbootshiro.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService service;



    @RequestMapping("register")
    public String register(User user) {
        try {
            service.register(user);
            System.out.println("保存成功");
            return "redirect:/login.jsp";
        } catch (Exception e) {
            System.out.println("保存失败");
            e.printStackTrace();
            return "redirect:/register.jsp";
        }


    }


    @RequestMapping("logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        System.out.println("已退出登录！");
        return "redirect:/login.jsp";

    }

    /**
     * 用来处理身份
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("login")
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
