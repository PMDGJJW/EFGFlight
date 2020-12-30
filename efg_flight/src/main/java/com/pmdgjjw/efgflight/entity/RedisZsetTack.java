package com.pmdgjjw.efgflight.entity;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @auth jian j w
 * @date 2020/7/26 14:54
 * @Description
 */
public class RedisZsetTack extends QuartzJobBean {

    @Autowired
    private  RedisTemplate redisTemplate;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        String format = new SimpleDateFormat("yyyy-MM").format(new Date());
        String forDay = new SimpleDateFormat("dd").format(new Date());

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        int daNum = Integer.valueOf(forDay)-1;

        redisTemplate.opsForZSet().removeRange(format+"-"+daNum+":hot",0,-1);

        int dayNum = Integer.valueOf(forDay);

        if (dayNum %7 == 0){

            double wk = dayNum / 7;
            int floor = (int) (Math.floor(wk)-1);

            redisTemplate.opsForZSet().removeRange(format+":week:"+floor+":hot",0,-1);
        }

        if ( forDay.equals("01")){

            String substring = format.substring(format.length()-2, format.length());
            String yyyy = new SimpleDateFormat("yyyy").format(new Date());

            int mounth = Integer.valueOf(substring)-1;

            String muth = String.valueOf(mounth);

            if (muth.length()==1){
                yyyy = yyyy+"-0"+mounth;
            }else {
                yyyy = yyyy+"-"+mounth;
            }
            redisTemplate.opsForZSet().removeRange(yyyy+":week:4:hot",0,-1);
        }

    }
}
