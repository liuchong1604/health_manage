package com.health;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Created with IDEA
 *
 * @author chong  liu
 * @create 2020/2/20 14:18
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
public class HealthGateWayApplication {
    public static void main(String[] args) {
        SpringApplication.run(HealthGateWayApplication.class);
    }
}
