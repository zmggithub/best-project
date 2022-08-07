package com.freemarker.demo;

import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * TODO
 *  Controller 和 RestController 知道有什么区别么？
 * @Author zmgab@foxmail.com
 * @Date 2022/7/26 11:21
 */
@Controller
public class ControllerIndex {

    @GetMapping(path = "/products")
    public String getProducts(Model model){
        model.addAttribute("products", Arrays.asList("iPad","iPhone","iPod"));
        return "products";
    }

    @GetMapping(path = "/logout")
    public String logout(HttpServletRequest request) throws ServletException {
        request.logout();
        return "/";
    }


}
