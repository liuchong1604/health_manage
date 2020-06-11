package com.health.dairy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * Created with IDEA
 *
 * @author chong  liu
 * @create 2020/4/28 10:16
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("com.health.dairy.mapper")
public class HealthDairyApplication {
    public static void main(String[] args) {
        SpringApplication.run(HealthDairyApplication.class);
    }
}
