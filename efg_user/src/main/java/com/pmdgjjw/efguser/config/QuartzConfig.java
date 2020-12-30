package com.pmdgjjw.efguser.config;

import com.pmdgjjw.efguser.Task.SpitTask;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @auth jian j w
 * @date 2020/7/12 10:26
 * @Description
 */
@Configuration
public class QuartzConfig {

    private static final String SPIT_TASK_IDENTITY = "SpitTaskQuartz";

    @Bean
    public JobDetail quartzDetail() {
        return JobBuilder.newJob(SpitTask.class).withIdentity(SPIT_TASK_IDENTITY).storeDurably().build();
    }

    @Bean
    public Trigger quartzTrigger() {

//                SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
//                .withIntervalInSeconds( 60)  //设置时间周期单位秒
//               .withIntervalInHours(18)  //两个小时执行一次
//                .repeatForever();

        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0 0 2 * * ?");
        return TriggerBuilder.newTrigger().forJob(quartzDetail())
                .withIdentity(SPIT_TASK_IDENTITY)
                .withSchedule(cronScheduleBuilder)
                .build();
    }

}
