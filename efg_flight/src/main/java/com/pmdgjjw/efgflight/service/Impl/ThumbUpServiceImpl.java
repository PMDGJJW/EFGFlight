package com.pmdgjjw.efgflight.service.Impl;

import com.pmdgjjw.efgflight.dao.ThumbUpTackMapper;
import com.pmdgjjw.efgflight.entity.ThumbUp;
import com.pmdgjjw.efgflight.entity.Thumbdw;
import com.pmdgjjw.efgflight.service.ThumbUpService;
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
 * @date 2020/7/13 0:17
 * @Description
 */
@Service
@Transactional
public class ThumbUpServiceImpl extends BaseImpl<ThumbUp> implements ThumbUpService {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    ThumbUpTackMapper thumbUpTackMapper;

    @Override
    public int upInsert() {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        Cursor<Map.Entry<Object, Object>> test = redisTemplate.opsForHash().scan("SpitThumbUp", ScanOptions.scanOptions().match("*").count(1000).build());
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

        List<ThumbUp> thumbUpList = new ArrayList<>();

        if (list.size()>0){
            for (int i = 0; i < list.size(); i++) {
                ThumbUp thumbUp = new ThumbUp();
                String[] split = list.get(i).split("::");
                thumbUp.setSid(split[0]);
                thumbUp.setUid(split[1]);
                thumbUpList.add(thumbUp);
            }

            int i = thumbUpTackMapper.thumbUpInsert(thumbUpList);

            return i;
        }else {
            return 0;
        }
    }
}
