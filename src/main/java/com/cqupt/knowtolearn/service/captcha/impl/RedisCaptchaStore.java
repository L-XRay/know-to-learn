package com.cqupt.knowtolearn.service.captcha.impl;

import com.cqupt.knowtolearn.service.captcha.ICaptchaStore;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author Ray
 * @date 2023/7/25 09:39
 * @description
 */
@Component("redisCaptchaStore")
public class RedisCaptchaStore implements ICaptchaStore {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void set(String key, String value, Integer expire) {
        stringRedisTemplate.opsForValue().set(key, value, expire, TimeUnit.MINUTES);
    }

    @Override
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public void remove(String key) {
        stringRedisTemplate.delete(key);
    }

    @Override
    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(stringRedisTemplate.hasKey(key));
    }

    @Override
    public Long ttl(String key) {
        return stringRedisTemplate.opsForValue().getOperations().getExpire(key);
    }
}
