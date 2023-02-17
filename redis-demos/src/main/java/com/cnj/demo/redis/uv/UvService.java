package com.cnj.demo.redis.uv;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author czz
 * @since 2023/2/2 下午9:22
 */
@Service
public class UvService {

    public static final String UV_PREFIX = "uv:page:";

    private final StringRedisTemplate redisTemplate;

    public UvService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void addUv(String pageId, String... userIds) {
        redisTemplate.opsForHyperLogLog().add(UV_PREFIX + pageId, userIds);
    }

    public Long countUv(String pageId){
        return redisTemplate.opsForHyperLogLog().size(UV_PREFIX + pageId);
    }
}
