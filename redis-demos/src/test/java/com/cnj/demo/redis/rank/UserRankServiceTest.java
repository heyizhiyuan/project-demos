package com.cnj.demo.redis.rank;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Sets;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ZSetOperations;

/**
 * @author czz
 * @since 2023/2/1 下午9:24
 */
@Slf4j
@SpringBootTest
public class UserRankServiceTest {
    
    @Autowired
    UserRankService userRankService;

    /**
     *
     */
    @Test
    public void addRankUser(){
        userRankService.addRankUser("user1", 80);
    }

    /**
     *
     */
    @Test
    public void addRankUsers(){
        userRankService.addRankUsers(Sets.set(
                ZSetOperations.TypedTuple.of("user2", 101d),
                ZSetOperations.TypedTuple.of("user3", 61d),
                ZSetOperations.TypedTuple.of("user4", 90d),
                ZSetOperations.TypedTuple.of("user5", 51d),
                ZSetOperations.TypedTuple.of("user6", 69d),
                ZSetOperations.TypedTuple.of("user7", 78d)
        ));
    }

    /**

     * @return
     */
    @Test
    public void range(){
        log.info("正序不带分值，{}", userRankService.range(0, -1));
    }

    /**
     * @return
     */
    @Test
    public void reverseRange(){
        log.info("倒序不带分值，{}", userRankService.reverseRange(0, -1));
    }

    /**
     * @return
     */
    @Test
    public void rangeByScoreWithScores(){
        log.info("正序带分值，{}", userRankService.rangeWithScores(0, -1));
    }

    /**
     * @return
     */
    @Test
    public void reverseRangeByScoreWithScores(){
        log.info("倒序带分值，{}", userRankService.reverseRangeWithScores(0, -1));
    }

}
