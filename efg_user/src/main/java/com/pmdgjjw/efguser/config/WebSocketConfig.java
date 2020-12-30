package com.pmdgjjw.efguser.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.socket.server.standard.ServerEndpointExporter;


/**
 * @auth jian j w
 * @date 2020/8/19 10:18
 * @Description
 */
@Configuration
public class WebSocketConfig  {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
