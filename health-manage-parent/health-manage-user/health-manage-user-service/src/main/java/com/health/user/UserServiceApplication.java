package com.health.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * Created with IDEA
 *
 * @author chong  liu
 * @create 2020/2/16 13:41
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.health.user.mapper")
public class UserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class);
    }
}
