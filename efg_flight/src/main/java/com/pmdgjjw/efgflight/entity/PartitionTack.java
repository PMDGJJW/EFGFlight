package com.pmdgjjw.efgflight.entity;

import com.pmdgjjw.efgflight.dao.PartitionMapper;
import com.pmdgjjw.efgflight.dao.SonPartitionMapper;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @auth jian j w
 * @date 2020/7/14 16:34
 * @Description
 */
public class PartitionTack  extends QuartzJobBean {

    @Autowired
    SonPartitionMapper sonPartitionMapper;

    @Autowired
    PartitionMapper partitionMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        List<Integer> son = sonPartitionMapper.selectID();

        List<Integer> par = partitionMapper.selectID();

        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());


        redisTemplate.executePipelined(new RedisCallback<Object>() {

            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {

                redisConnection.openPipeline();

                String key = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

                for (Integer sonid : son) {
                        String value = String.valueOf(sonid);
                        redisConnection.zIncrBy(key.getBytes(),0,value.getBytes());
                }

                return null;
            }
        });


    }
}
