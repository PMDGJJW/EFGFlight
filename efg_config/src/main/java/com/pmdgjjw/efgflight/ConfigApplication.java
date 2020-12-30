package com.pmdgjjw.efgflight;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @auth jian j w
 * @date 2020/8/27 22:59
 * @Description
 */
@EnableConfigServer         //开启SpringCloudConfig集中配置服务器
@SpringBootApplication
public class ConfigApplication {

    public static void main(String[] args) {

        SpringApplication.run(ConfigApplication.class);

    }
}
