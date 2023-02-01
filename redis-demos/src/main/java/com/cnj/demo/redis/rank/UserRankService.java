package com.cnj.demo.redis.rank;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author czz
 * @since 2023/2/1 下午9:14
 */
@Service
public class UserRankService {

    public static final String RANKING = "ranking1";

    private final StringRedisTemplate redisTemplate;

    public UserRankService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     *
     * @param userId
     * @param score
     */
    public void addRankUser(String userId, double score){
        redisTemplate.opsForZSet().add(RANKING, userId, score);
    }

    /**
     *
     * @param users
     */
    public void addRankUsers(Set<ZSetOperations.TypedTuple<String>> users){
        redisTemplate.opsForZSet().add(RANKING, users);
    }

    /**
     *  @param start
     * @param end
     * @return
     */
    public Set<String> range(int start, int end){
        return redisTemplate.opsForZSet().range(RANKING, start, end);
    }

    /**
     *  @param start
     * @param end
     * @return
     */
    public Set<String> reverseRange(int start, int end){
        return redisTemplate.opsForZSet().reverseRange(RANKING, start, end);
    }

    /**
     *  @param start
     * @param end
     * @return
     */
    public Set<ZSetOperations.TypedTuple<String>> rangeWithScores(int start, int end){
        return redisTemplate.opsForZSet().rangeWithScores(RANKING, start, end);
    }

    /**
     *  @param start
     * @param end
     * @return
     */
    public Set<ZSetOperations.TypedTuple<String>> reverseRangeWithScores(int start, int end){
        return redisTemplate.opsForZSet().reverseRangeWithScores(RANKING, start, end);
    }

}
