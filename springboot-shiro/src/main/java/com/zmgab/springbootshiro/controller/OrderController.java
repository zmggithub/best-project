package com.zmgab.springbootshiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: zmg
 */
@Controller
@RequestMapping("order")
public class OrderController {

    @RequestMapping("save")
    @RequiresRoles(value = {"admin", "user"}) // 用来判断角色 同时具有所有角色
    @RequiresPermissions("user:update:01")
    public String save() {

        // 获取主题对象
        Subject subject = SecurityUtils.getSubject();

        if (subject.hasRole("admin")) {
            System.out.println("保存订单");
        } else {
            System.out.println("无权访问");
        }

        // 基于权限字符串

        return "redirect:/index.jsp";
    }
}
