package com.pmdgjjw.efgflight.config;

import com.pmdgjjw.efgflight.entity.*;
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

    private static final String THUMB_DWTASK_IDENTITY = "ThumbDwTaskQuartz";
    private static final String THUMB_UPTASK_IDENTITY = "ThumbUpTaskQuartz";
    private static final String THUMB_PARTITION_IDENTITY = "PartitionTaskQuartz";
    private static final String REDISZSET_PARTITION_IDENTITY = "RedisZsetTaskQuartz";



    @Bean
    public JobDetail dwquartzDetail() {
        return JobBuilder.newJob(ThumbDwTask.class).withIdentity(THUMB_DWTASK_IDENTITY).storeDurably().build();
    }

    @Bean
    public Trigger dwquartzTrigger() {
//        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
//                .withIntervalInSeconds((int) (60*60*18.7))  //设置时间周期单位秒
//               .withIntervalInHours(18)  //两个小时执行一次
//                .repeatForever();

        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0 30 1 * * ?");

        return TriggerBuilder.newTrigger().forJob(dwquartzDetail())
                .withIdentity(THUMB_DWTASK_IDENTITY)
                .withSchedule(cronScheduleBuilder)
                .build();
    }

    @Bean
    public JobDetail upquartzDetail() {
        return JobBuilder.newJob(ThumbUpTack.class).withIdentity(THUMB_UPTASK_IDENTITY).storeDurably().build();
    }

    @Bean
    public Trigger upquartzTrigger() {

        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0 45 1 * * ?");

//                SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
//                .withIntervalInSeconds(130)  //设置时间周期单位秒
//               .withIntervalInHours(18)  //两个小时执行一次
//                .repeatForever();

        return TriggerBuilder.newTrigger().forJob(upquartzDetail())
                .withIdentity(THUMB_UPTASK_IDENTITY)
                .withSchedule(cronScheduleBuilder)
                .build();
    }

    @Bean
    public JobDetail partitionquartzDetail() {
        return JobBuilder.newJob(PartitionTack.class).withIdentity(THUMB_PARTITION_IDENTITY).storeDurably().build();
    }

    @Bean
    public Trigger partitionquartzTrigger() {

        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("59 59 23 * * ?");

        return TriggerBuilder.newTrigger().forJob(partitionquartzDetail())
                .withIdentity(THUMB_PARTITION_IDENTITY)
                .withSchedule(cronScheduleBuilder)
                .build();
    }

    @Bean
    public JobDetail redisZsetquartzDetail() {
        return JobBuilder.newJob(RedisZsetTack.class).withIdentity(REDISZSET_PARTITION_IDENTITY).storeDurably().build();
    }

    @Bean
    public Trigger redisZsetquartzTrigger() {

        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("00 30 2 * * ?");

        return TriggerBuilder.newTrigger().forJob(redisZsetquartzDetail())
                .withIdentity(REDISZSET_PARTITION_IDENTITY)
                .withSchedule(cronScheduleBuilder)
                .build();
    }
}
