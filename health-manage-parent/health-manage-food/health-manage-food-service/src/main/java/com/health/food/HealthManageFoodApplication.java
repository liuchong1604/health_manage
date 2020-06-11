package com.health.food;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * Created with IDEA
 *
 * @author chong  liu
 * @create 2020/4/28 8:38
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.health.food.mapper")
public class HealthManageFoodApplication {
    public static void main(String[] args) {
        SpringApplication.run(HealthManageFoodApplication.class);
    }
}
