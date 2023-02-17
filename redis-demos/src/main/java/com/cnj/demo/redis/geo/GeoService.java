package com.cnj.demo.redis.geo;

import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author czz
 * @since 2023/2/15 下午8:55
 */
@Service
public class GeoService {

    private final static String KEY = "geo:drinks";

    private final StringRedisTemplate redisTemplate;

    public GeoService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void add(String drinkName, Point point){
        redisTemplate.opsForGeo().add(KEY, point, drinkName);
    }

    /**
     *
     * @param drinkNames
     * @return
     */
    public List<Point> get(String... drinkNames){
        return redisTemplate.opsForGeo().position(KEY, drinkNames);
    }

    /**
     * 删除
     * @param drinkNames
     * @return
     */
    public Long del(String... drinkNames){
        return redisTemplate.opsForGeo().remove(KEY, drinkNames);
    }

    /**
     *
     * @return
     */
    public GeoResults<RedisGeoCommands.GeoLocation<String>> getNearbyByPoint(Point point, Distance distance, long limit){
        Circle circle = new Circle(point, distance);
        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs
                .newGeoRadiusArgs()
                // 包含距离
                .includeDistance()
                // 包含坐标
                .includeCoordinates()
                //排序
                .sortAscending()
                //获取数量
                .limit(limit);
        return redisTemplate.opsForGeo().radius(KEY, circle, args);
    }

    /**
     * 根据一个位置，获取指定范围内的其他位置
     * @param placeName
     * @param distance
     * @return
     */
    public GeoResults<RedisGeoCommands.GeoLocation<String>> getNearByPlace(String placeName, Distance distance) {
        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs.
                newGeoRadiusArgs()
                .includeDistance()
                .includeCoordinates()
                .sortAscending()
                //获取数量
                .limit(5);
        return redisTemplate.opsForGeo().radius(KEY, placeName, distance, args);
    }

    /**
     *
     * @return
     */
    public GeoResults<RedisGeoCommands.GeoLocation<String>> getNearbyByPoint(Point point, Distance distance){
        return this.getNearbyByPoint(point, distance, 5);
    }

    /**
     *
     * @param names
     * @return
     */
    public List<String> getGeoHash(String... names){
        return this.redisTemplate.opsForGeo().hash(KEY, names);
    }

    /**
     *
     * @param form
     * @param to
     * @return
     */
    public Distance getDist(String form, String to){
        return this.redisTemplate.opsForGeo().distance(KEY, form, to);
    }

}
