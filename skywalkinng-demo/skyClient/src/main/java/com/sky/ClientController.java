package com.sky;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * TODO
 *
 * @Author zmgab@foxmail.com
 * @Date 2023/2/27 16:11
 */
@RestController
public class ClientController {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ClientController.class);

    @Resource
    RestTemplate restTemplate;

    @RequestMapping("/clientRequest")
    public String clientRequest() {
        log.info("进入参数： clientRequest");
        String url = "http://localhost:28888/monitor";
        HttpEntity<Object> request = new HttpEntity<>(null,null);

        ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        log.info("返回结果：" + exchange);
        return exchange.toString();
    }


    @GetMapping("/clientRequest2")
    public String clientRequest2() {
        log.info("这是一个测试方法！");
        return "ok";
    }

}
