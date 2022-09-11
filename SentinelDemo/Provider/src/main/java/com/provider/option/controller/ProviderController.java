package com.provider.option.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Spring Boot Application in default package
 *
 * @Author zmgab@foxmail.com
 * @Date 2022/9/6 15:34
 */
@RestController
public class ProviderController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/instances")
    public List<ServiceInstance> instances(){
        List<ServiceInstance> provider = discoveryClient.getInstances("provider");
        return provider;
    }

    @GetMapping("/index")
    public String index(){
        return "consumer随机远程调用provier";
    }
}
