package com.cnj.demo.redis.geo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;

import java.util.List;

/**
 * @author czz
 * @since 2023/2/15 下午9:19
 */
@Slf4j
@SpringBootTest
public class GeoServiceTest {

    @Autowired
    GeoService geoService;

    @Test
    public void add(){
        geoService.add("starbucks", new Point(116.62445, 39.86206));
        geoService.add("yidiandian", new Point(117.3514785, 38.7501247));
        geoService.add("xicha", new Point(116.538542, 39.754129559));
    }

    @Test
    public void get(){
        List<Point> points = geoService.get("yidiandian", "xicha");
        log.info("获取到 yidiandian 和 xicha 坐标:{}", points);
    }

    @Test
    public void del(){
        Long aLong = geoService.del("xicha", "starbucks");
        log.info("删除结果:{}", aLong);
        log.info("查询已删除的数据:{}", geoService.get("starbucks", "xicha"));
        geoService.add("starbucks", new Point(116.62445, 39.86206));
        geoService.add("xicha", new Point(116.538542, 39.754129559));
        log.info("重新添加后查询数据:{}", geoService.get("starbucks", "xicha"));
    }

    @Test
    public void getNearbyByPoint(){
        GeoResults<RedisGeoCommands.GeoLocation<String>> results = geoService.getNearbyByPoint(new Point(116, 39), new Distance(120, Metrics.KILOMETERS));
        log.info("根据坐标查询附近坐标:{}", results);
    }

    @Test
    public void getNearByPlace(){
        GeoResults<RedisGeoCommands.GeoLocation<String>> results = geoService.getNearByPlace("starbucks", new Distance(120, Metrics.KILOMETERS));
        log.info("根据场所查询附近坐标:{}", results);
    }

    @Test
    public void getDist(){
        Distance dist = geoService.getDist("xicha", "starbucks");
        log.info("starbucks 距离 xicha {}", dist);
    }

}
