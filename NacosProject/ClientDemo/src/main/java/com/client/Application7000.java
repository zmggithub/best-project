package com.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableDiscoveryClient
public class Application7000 {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(Application7000.class, args);
    }
}
