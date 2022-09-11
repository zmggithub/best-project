package com.consumer.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Spring Boot Application in default package
 *
 * @Author zmgab@foxmail.com
 * @Date 2022/9/6 15:34
 */
@RestController
public class ConsumerController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/instances")
    public List<ServiceInstance> instances(){
        List<ServiceInstance> provider = discoveryClient.getInstances("provider");
        return provider;
    }

    @GetMapping("/indexerror")
    public String indexerror(){
        /*
        java.lang.IllegalStateException: No instances available for 192.168.153.1
        添加了@LoadBalanced注解后不能再使用192.168.153.1了，而是使用Nacos注册的服务名CLOUD-PAYMENT-SERVICE
         */
        List<ServiceInstance> provider = discoveryClient.getInstances("provider");
        int index = ThreadLocalRandom.current().nextInt(provider.size());
        String url = provider.get(index).getUri()+"/index";
        return "consumer随机远程调用provier："+this.restTemplate.getForObject(url, String.class);
    }


    /**
     *   Ribbon 负载均衡 @LoadBalanced
     * @return
     */
    @GetMapping("/index")
    public String index(){
        return "consumer远程调用provier："+this.restTemplate.getForObject("http://provider/index", String.class);
    }


    @GetMapping("/hot")
    @SentinelResource("hot")
    public String hot(
            @RequestParam(value = "num1",required = false) Integer num1,
            @RequestParam(value = "num2",required = false) Integer num2){
        return num1+"-"+num2;
    }

}
