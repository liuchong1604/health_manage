package com.health.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * cors解决跨域问题
 * Created with IDEA
 *
 * @author chong  liu
 * @create 2019/12/21 11:20
 */
@Configuration
public class HealthCorsConfiguration {
    @Bean
    public CorsFilter corsFilter(){
        //初始化cors配置源对象
        CorsConfiguration configuration = new CorsConfiguration();
        //允许跨域的域名,如果要携带Cookie,不能写*,*代表所有域名都可以跨域访问
        configuration.addAllowedOrigin("http://manage.health.com");
        configuration.addAllowedOrigin("http://www.health.com");
        configuration.setAllowCredentials(true);//允许携带Cookie
        configuration.addAllowedMethod("*");//代表所有的请求方法,Get Post Put Delete...
        configuration.addAllowedHeader("*");//允许携带任何头信息

        UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
        configurationSource.registerCorsConfiguration("/**",configuration);

        //返回corsFilter实例,参数:cors配置源对象
        return new CorsFilter(configurationSource);
    }
}
