package com.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableDiscoveryClient
public class Application7000 {
    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext run = SpringApplication.run(Application7000.class, args);

        while (true) {
            //当动态配置刷新时，会更新到 Enviroment中，因此这里每隔3秒中从Enviroment中获取配置
            String userName = run.getEnvironment().getProperty("common.name");
            String userAge = run.getEnvironment().getProperty("common.age");
            System.err.println("common name:" + userName + "; age: " + userAge);
            TimeUnit.SECONDS.sleep(3);

        }
    }

}
