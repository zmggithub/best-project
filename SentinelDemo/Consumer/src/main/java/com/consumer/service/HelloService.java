package com.consumer.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.stereotype.Service;

/**
 * TODO
 *
 * @Author zmgab@foxmail.com
 * @Date 2022/9/6 16:48
 */
@Service
public class HelloService {

    @SentinelResource("test")
    public void test(){
        System.out.println("test");
    }

}
