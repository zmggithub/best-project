package com.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * springboot项目解决报错：spring boot application in default package
 * (1)springboot的启动方法不能直接在java目录下，要放在default 包下。
 * (2)另外官方给出的解决方案是：
 * @springbootApplication 注解失效的情况下，推荐使用@CompentScan 和@EnableAutoConfiguration进行代替；
 * (3) @springbootApplication是springboot项目的核心注解。
 * (4)springboot会自动扫描@springbootApplication所在类的同级包，以及下级包里的Bean。建议入口类放置在groupId+artifactId组和的包下。
 *
 * @Author zmgab@foxmail.com
 * @Date 2022/9/6 15:33
 */
@SpringBootApplication
public class ProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class, args);
    }
}
