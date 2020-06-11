package com.health;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Created with IDEA
 *
 * @author chong  liu
 * @create 2020/2/2 14:43
 */
@SpringBootApplication
@EnableEurekaServer
public class HealthManageRegistryApplication {
    public static void main(String[] args) {
        SpringApplication.run(HealthManageRegistryApplication.class);
    }
}
