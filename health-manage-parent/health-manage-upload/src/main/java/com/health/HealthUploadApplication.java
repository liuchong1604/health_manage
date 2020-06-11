package com.health;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created with IDEA
 *
 * @author chong  liu
 * @create 2020/4/15 16:07
 */
@SpringBootApplication
@EnableDiscoveryClient
public class HealthUploadApplication {
    public static void main(String[] args) {
        SpringApplication.run(HealthUploadApplication.class);
    }
}
