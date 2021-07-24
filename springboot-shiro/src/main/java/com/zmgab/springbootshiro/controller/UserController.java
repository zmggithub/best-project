package com.zmgab.springbootshiro.controller;

import com.zmgab.springbootshiro.entity.User;
import com.zmgab.springbootshiro.service.UserService;
import com.zmgab.springbootshiro.util.VerifyCodeUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService service;

    @RequestMapping("getImage")
    public void getImage(HttpSession session, HttpServletResponse response) throws IOException {
        // 生成验证码
        String code = VerifyCodeUtils.generateVerifyCode(4);
        // 验证码放入session
        session.setAttribute("code", code);
        // 验证码存入图片
        ServletOutputStream os = response.getOutputStream();
        response.setContentType("image/png");
        VerifyCodeUtils.outputImage(220, 60, os, code);

    }

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
     *
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("login")
    public String login(String username, String password, String code, HttpSession session) {
        // 比较验证码
        String codes = (String) session.getAttribute("code");
        try {
            if (codes.equalsIgnoreCase(code)) {
                // 获取主体对象
                Subject subject = SecurityUtils.getSubject();
                UsernamePasswordToken authenticationToken = new UsernamePasswordToken(username, password);

                subject.login(authenticationToken);
                System.out.println("登录成功！");
                return "redirect:/index.jsp";
            } else {
                throw new RuntimeException("验证码错误");
            }
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            System.out.println("认证失败:用户名不存在");
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            System.out.println("认证失败: 密码错误");
        } catch (RuntimeException e) {
            System.out.println("验证码错误:" + e.getMessage());
        } catch (Exception e) {
            System.out.println("认证失败");
        }
        return "redirect:/login.jsp";
    }
}
