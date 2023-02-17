package com.cnj.demo.redis.uv;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author czz
 * @since 2023/2/2 下午9:26
 */
@Slf4j
@SpringBootTest
public class UvServiceTest {

    @Autowired
    UvService uvService;

    @Test
    public void addUv() {
        for (int i = 0; i < 10000;i++){
            uvService.addUv("2", "user" + ( i +1));
        }
    }

    @Test
    public void countUv(){
        Long aLong = uvService.countUv("2");
        log.info("uv: {}", aLong);
    }


}
