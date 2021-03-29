package com.playmusical.playmusicalweb.service;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

public class SessionDBServiceRedisImpl implements SessionDBService {

    @Autowired
    private RedisTemplate<String, String> sessionRedisTemplate;

    @Override
    public boolean isExist(String token) {
        return sessionRedisTemplate.hasKey(token);
    }

    @Transactional
    @Override
    public void insert(String token, String userId) {
        ValueOperations<String, String> valueOperations = sessionRedisTemplate.opsForValue();
        valueOperations.set(token, userId, 10, TimeUnit.MINUTES);
    }

    @Transactional
    @Override
    public void delete(String token) {
        sessionRedisTemplate.delete(token);
    }

    @Override
    public String getUserId(String token) {
        if (isExist(token)) {
            ValueOperations<String, String> valueOperations = sessionRedisTemplate.opsForValue();
            return valueOperations.get(token);
        }
        return null;
    }

    @Override
    public void updateExpiredTime(String token, LocalDateTime updateTime) {
        sessionRedisTemplate.expire(token, 10, TimeUnit.MINUTES);
    }

}
