package com.pmdgjjw.efgflight;

import com.pmdgjjw.efgflight.util.EsUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @auth jian j w
 * @date 2020/6/25 21:09
 * @Description
 */
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
public class EFGFlightApplication {

    @Bean
    public EsUtil getEsUtil(){
        return new EsUtil();
    }

    public static void main(String[] args) {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        SpringApplication.run(EFGFlightApplication.class,args);
    }

    @Bean
    public ThreadPoolTaskExecutor brianThreadPool(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程数
        executor.setCorePoolSize(8);
        //最大线程数
        executor.setMaxPoolSize(16);
        //队列中最大的数
        executor.setQueueCapacity(8);
        //县城名称前缀
        executor.setThreadNamePrefix("brianThreadPool_");
        //rejectionPolicy：当pool已经达到max的时候，如何处理新任务
        //callerRuns：不在新线程中执行任务，而是由调用者所在的线程来执行
        //对拒绝task的处理策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //线程空闲后最大的存活时间
        executor.setKeepAliveSeconds(60);
        //初始化加载
        executor.initialize();
        return executor;
    }



}
