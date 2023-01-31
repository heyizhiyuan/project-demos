package com.cnj.demo.redis.lottery;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author czz
 * @since 2023/1/30 下午9:38
 */
@Slf4j
@SpringBootTest
public class UserLotteryServiceTest {

    @Autowired
    UserLotteryService userLotteryService;

    /**
     *
     */
    @Test
    public void addUsers(){
        Long aLong = userLotteryService.addUsers("100", new String[]{"1", "2", "3", "4", "5"});
        log.info("addUsers result:{}", aLong);
    }

    /**
     *
     */
    @Test
    public void draw(){
        List<String> result = userLotteryService.draw("100", 2);
        log.info("draw result:{}", result);
    }

}
