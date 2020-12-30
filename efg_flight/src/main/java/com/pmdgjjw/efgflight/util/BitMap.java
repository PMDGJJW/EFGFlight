package com.pmdgjjw.efgflight.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

/**
 * @auth jian j w
 * @date 2020/6/29 21:22
 * @Description
 */
@Component
public class BitMap {

     @Autowired
     RedisTemplate redisTemplate ;

    private static final Boolean  YES = true;
    private static final Boolean  NO = false;

    public void setCode(){
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
    }

    public  Boolean setBit(String key,long offset,int flag){

        setCode();

        if (flag==0){
           return redisTemplate.opsForValue().setBit(key,offset,NO);
        }else {
           return redisTemplate.opsForValue().setBit(key,offset,YES);
        }

    }

    public  Boolean getBit(String key,int offset){
        return redisTemplate.opsForValue().getBit(key,offset);
    }

    public  Long bitCount(String key){
        Long count = (Long) redisTemplate.execute((RedisCallback<Long>) con -> con.bitCount(key.getBytes()));
        return count;
    }

    public  Long bitOp(String type,String newkey,String key1,String key2){
        Long count = 0L;
        if (type.equals("and")){
             count = (Long) redisTemplate.execute((RedisCallback<Long>) con -> con.bitOp(RedisStringCommands.BitOperation.AND, newkey.getBytes(), key1.getBytes(), key2.getBytes()));
        }
         if (type.equals("or")){
             count = (Long) redisTemplate.execute((RedisCallback<Long>) con -> con.bitOp(RedisStringCommands.BitOperation.OR, newkey.getBytes(), key1.getBytes(), key2.getBytes()));
        }
        if (type.equals("not")){
             count = (Long) redisTemplate.execute((RedisCallback<Long>) con -> con.bitOp(RedisStringCommands.BitOperation.NOT, newkey.getBytes(), key1.getBytes(), key2.getBytes()));
        }
        if (type.equals("xor")){
             count = (Long) redisTemplate.execute((RedisCallback<Long>) con -> con.bitOp(RedisStringCommands.BitOperation.XOR, newkey.getBytes(), key1.getBytes(), key2.getBytes()));
        }
        return count;
    }
}
