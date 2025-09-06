package com.practice.JournalApp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class TokenBlacklistService {

    private final RedisTemplate<String, String> redisTemplate;
    private final String BLACKLIST_PREFIX = "blacklist:";

    public void blackListToken(String token, long expirationInMillis){
        redisTemplate.opsForValue().set(BLACKLIST_PREFIX + token, "true",
                Duration.ofMillis(expirationInMillis));
    }
    public boolean isTokenBlacklisted(String token){
        return redisTemplate.hasKey(BLACKLIST_PREFIX + token);
    }
}
