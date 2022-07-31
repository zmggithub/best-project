package com.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class IndexController {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("index")
    public String index(){
        /**
         * 9001  9002
         */
        return  restTemplate.getForObject("http://register-server/index", String.class);

    }
}
