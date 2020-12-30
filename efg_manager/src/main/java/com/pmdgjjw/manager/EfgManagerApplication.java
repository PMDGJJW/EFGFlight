package com.pmdgjjw.manager;

import com.netflix.zuul.ZuulFilter;
import com.pmdgjjw.util.JwtUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.EnableZuulServer;
import org.springframework.context.annotation.Bean;

/**
 * @auth jian j w
 * @date 2020/8/5 22:58
 * @Description
 */
@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
public class EfgManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EfgManagerApplication.class);
    }

    @Bean
    public JwtUtil jwtUtil(){
        return new JwtUtil();
    }



}
