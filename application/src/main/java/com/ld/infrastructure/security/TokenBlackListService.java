package com.ld.infrastructure.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class TokenBlackListService {

    @Autowired
    private  RedisTemplate<String, String> redisTemplate;

    public void blacklistToken(String token, long timeToExpire) {
        try {
            redisTemplate.opsForValue().set(token, "revoked", timeToExpire, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new RuntimeException("Failed to blacklist token", e);
        }
    }

    public boolean isBlacklisted(String token) {
        try {
            return Boolean.TRUE.equals(redisTemplate.hasKey(token));
        } catch (Exception e) {
            return false;
        }
    }
}
