package com.pmdgjjw.efguser;

import io.goeasy.GoEasy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.pmdgjjw.util.IPUtils;
import com.pmdgjjw.util.IdWorker;
import com.pmdgjjw.util.JwtUtil;

/**
 * @auth jian j w
 * @date 2020/7/27 17:33
 * @Description
 */
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
public class EFGUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(EFGUserApplication.class,args);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtUtil jwtUtil(){
        return new JwtUtil();
    }

    @Bean
    public IdWorker idWorker(){
        return new IdWorker();
    }

    @Bean
    public IPUtils ipUtils(){
        return new IPUtils();
    }

    @Bean
    public GoEasy getEasy(){
        GoEasy easy = new GoEasy("rest-hangzhou.goeasy.io", "BC-c1da2ba5259e4d8484ec3b0c6de16859");

        return easy;
    }

}
