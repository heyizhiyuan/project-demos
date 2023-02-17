package com.cnj.demo.redis.usersign;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.MonthDay;
import java.util.Map;

/**
 * @author czz
 * @since 2023/2/8 下午9:13
 */
@Slf4j
@SpringBootTest
public class UserSignServiceTest {

    @Autowired
    UserSignService userSignService;

    /**
     * 当月打卡情况
     */
    @Test
    public void querySignedInMonth(){
        userSignService.sign("860",0, true);
        userSignService.sign("860",1, true);
        if(MonthDay.now().getDayOfMonth() > 1){
            userSignService.sign("860", MonthDay.now().getDayOfMonth() - 2, true);
        }
        userSignService.sign("860", MonthDay.now().getDayOfMonth() - 1, true);
        Map<String, Boolean> map = userSignService.querySignedInMonth("860");
        log.info("当月打卡情况: {}", map);
    }

    /**
     * 当月打卡天数
     */
    @Test
    public void countSignedInDayOfMonth(){
        long signedCount = userSignService.countSignedInDayOfMonth("860");
        log.info("当月打卡天数:{}", signedCount);
    }

    /**
     * 连续打卡天数
     */
    @Test
    public void countContinuousSignOfMonth(){
        long signDays = userSignService.countContinuousSignOfMonth("860");
        log.info("连续打卡天数:{}", signDays);
    }

}
