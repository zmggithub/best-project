package com.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServerController {

    @Autowired
    DiscoveryClient discoveryClient;
    @Autowired
    ServerProperties serverProperties;

    @GetMapping("index")
    public  String index(){
        return serverProperties.getPort().toString();
    }

}
