package com.cnj.demo.redis.lottery;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author czz
 * @since 2023/1/30 下午9:29
 */
@Service
public class UserLotteryService {

    private final String KEY_LOTTERY_PREFIX = "lottery:";

    private final StringRedisTemplate redisTemplate;

    public UserLotteryService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     *  @param lotteryId
     * @param userIds
     * @return
     */
    public Long addUsers(String lotteryId, String[] userIds){
        return redisTemplate.opsForSet().add(KEY_LOTTERY_PREFIX + lotteryId, userIds);
    }

    /**
     *
     * @param lotteryId
     * @return
     */
    public List<String> draw(String lotteryId, int number){
        return redisTemplate.opsForSet().pop(KEY_LOTTERY_PREFIX + lotteryId, number);
    }

}
