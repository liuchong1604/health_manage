package com.health.item;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * Created with IDEA
 *
 * @author chong  liu
 * @create 2020/3/20 21:02
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.health.item.mapper")
public class HealthItemApplication {
    public static void main(String[] args) {
        SpringApplication.run(HealthItemApplication.class);
    }
}
