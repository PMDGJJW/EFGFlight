package com.pmdgjjw.efguser.service.Impl;


import com.pmdgjjw.efguser.dao.GoldCheckMapper;
import com.pmdgjjw.efguser.entity.GoldCheck;
import com.pmdgjjw.efguser.service.GoldTackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @auth jian j w
 * @date 2020/7/12 15:08
 * @Description
 */
@Service
@Transactional
public class GoldTackServiceImpl extends BaseImpl<GoldCheck> implements GoldTackService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private GoldCheckMapper goldCheckMapper;

    @Override
    public int GoldTackInsert() {

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        Cursor<Map.Entry<Object, Object>> test = redisTemplate.opsForHash().scan("goldTack", ScanOptions.scanOptions().match("*").count(1000).build());
        List<String> list = new ArrayList<>();
        while (test.hasNext()) {
            String key = (String) test.next().getKey();
            list.add(key);
        }
        //关闭cursor
        try {
            test.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<GoldCheck> goldChecklist = new ArrayList<>();

        if (list.size()>0){
            for (int i = 0; i < list.size(); i++) {
                GoldCheck goldCheck = new GoldCheck();
                String[] split = list.get(i).split("::");
                goldCheck.setCid(Integer.valueOf(split[0]));
                goldCheck.setUid(Long.valueOf(split[1]));
                goldChecklist.add(goldCheck);
            }

            int i = goldCheckMapper.SpitTackInsert(goldChecklist);

            return i;
        }else {
            return 0;
        }


    }
}
