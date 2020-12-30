package com.pmdgjjw.efgflight.service.Impl;

import com.pmdgjjw.efgflight.dao.SysUserMapper;
import com.pmdgjjw.efgflight.entity.MailUser;
import com.pmdgjjw.efgflight.entity.SysUser;
import com.pmdgjjw.efgflight.service.SysUserService;
import com.pmdgjjw.efgflight.util.CheckCode;
import com.pmdgjjw.efgflight.util.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @auth jian j w
 * @date 2020/6/28 22:18
 * @Description
 */
@Service
@Transactional
public class SysUserServiceImpl extends BaseImpl<SysUser> implements SysUserService {

    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    private EmailUtil emailUtil;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Integer typeCheck(Long id) {
        Integer integer = sysUserMapper.typeCheck(id);
        return integer;
    }


}
