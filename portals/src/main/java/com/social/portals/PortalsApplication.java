package com.social.portals;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan(basePackages = {"common.portals","com.social.portals.*"})
@EnableFeignClients(basePackages = {"common.feign"})
public class PortalsApplication {
    public static void main(String[] args) {
        SpringApplication.run(PortalsApplication.class, args);
    }

}

