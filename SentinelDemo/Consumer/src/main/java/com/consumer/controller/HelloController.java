package com.consumer.controller;

import com.consumer.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @Author zmgab@foxmail.com
 * @Date 2022/9/6 16:48
 */
@RestController
public class HelloController {

    @Autowired
    HelloService helloService;

    @GetMapping("/test1")
    public String test1(){
        this.helloService.test();
        return "test1";
    }

    @GetMapping("/test2")
    public String test2(){
        this.helloService.test();
        return "test2";
    }

}
