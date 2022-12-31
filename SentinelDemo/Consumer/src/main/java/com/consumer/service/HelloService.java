package com.consumer.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.stereotype.Service;

/**
 * TODO
 *
 * @Author zmgab@foxmail.com
 * @Date 2022/9/6 16:48
 */
@Service
public class HelloService {

    @SentinelResource(value="RESOURCE_NAME", blockHandler = "handleException", fallback = "fallbackException")
    public void test(){
        System.out.println("test");
    }

    public void handleException(BlockException ex){
        System.out.println("被流控了");
    }

    public void fallbackException(Throwable t){
        System.out.println("被异常降级了");
    }
}
