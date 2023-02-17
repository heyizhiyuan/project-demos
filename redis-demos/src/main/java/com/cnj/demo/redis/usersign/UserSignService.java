package com.cnj.demo.redis.usersign;

import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

/**
 * @author czz
 * @since 2023/2/7 下午9:45
 */
@Service
public class UserSignService {

    private final StringRedisTemplate stringRedisTemplate;

    public UserSignService(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 按照月份和用户ID生成用户签到标识 userSign:560:2021-08
     *
     * @param userId    用户id
     * @param yearMonth
     * @return
     */
    private String signKeyWitMouth(String userId, YearMonth yearMonth) {
        return "userSign:" + userId + ":" + yearMonth;
    }

    /**
     * 按照月份和用户ID生成用户签到标识 userSign:560:2021-08
     *
     * @param userId 用户id
     * @return
     */
    private String signKeyWitMouth(String userId) {
        return this.signKeyWitMouth(userId, YearMonth.now());
    }

    /**
     * @param userId
     * @param offset
     * @param tag
     * @return
     */
    public Boolean sign(String userId, long offset, boolean tag) {
        return stringRedisTemplate.opsForValue().setBit(this.signKeyWitMouth(userId), offset, tag);
    }

    /**
     * 统计计数
     *
     * @param key 用户标识
     * @return
     */
    public Long bitCount(String key) {
        return stringRedisTemplate.execute((RedisCallback<Long>) redisConnection -> redisConnection.bitCount(key.getBytes()));
    }

    /**
     * 获取多字节位域
     */
    public List<Long> bitfield(String buildSignKey, int limit, long offset) {
        return this.stringRedisTemplate
                .opsForValue()
                .bitField(buildSignKey, BitFieldSubCommands.create().get(BitFieldSubCommands.BitFieldType.unsigned(limit)).valueAt(offset));
    }

    /**
     * 判断是否被标记
     *
     * @param key
     * @param offset
     * @return
     */
    public Boolean contains(String key, long offset) {
        return this.stringRedisTemplate.opsForValue().getBit(key, offset);
    }

    /**
     * 用户今天是否签到
     *
     * @param userId
     * @return
     */
    public int checkSign(String userId) {
        String signKey = this.signKeyWitMouth(userId);
        long offset = MonthDay.now().getDayOfMonth() - 1;
        return this.contains(signKey, offset) ? 1 : 0;
    }

    /**
     * 查询用户当月签到日历
     *
     * @param userId
     * @return
     */
    public Map<String, Boolean> querySignedInMonth(String userId) {
        String signKey = this.signKeyWitMouth(userId);
        LocalDate localDate = LocalDate.now();
        int lengthOfMonth = localDate.lengthOfMonth();
        List<Long> longs = this.bitfield(signKey, lengthOfMonth, 0);
        System.out.println("longs:" + longs);
        Map<String, Boolean> result = new TreeMap<>();
        if (CollectionUtils.isEmpty(longs)) {
            return result;
        }
        Long signFlag = Optional.ofNullable(longs.get(0)).orElse(0L);
        for (int i = lengthOfMonth; i > 0; i--) {
            LocalDate dayOfMonth = localDate.withDayOfMonth(i);
            //如果末尾为1（已签到），这时signFlag是奇数，做右移运算会损失精度，导致左移动无法还原成原数
            //如果末尾为0（未签到），这时signFlag是偶数，做右移运算不会损失精度，导致左移动依然还可以原成原数
            result.put(dayOfMonth.toString(), signFlag >> 1 << 1 != signFlag);
            signFlag >>= 1;
        }
        return result;
    }

    /**
     * 用户签到
     *
     * @param userId
     * @return
     */
    public boolean signWithUserId(String userId) {
        int dayOfMonth = MonthDay.now().getDayOfMonth();
        String signKey = this.signKeyWitMouth(userId);
        long offset = dayOfMonth - 1;
        return Boolean.TRUE.equals(this.sign(signKey, offset, Boolean.TRUE));
    }

    /**
     * 统计当前月份一共签到天数
     *
     * @param userId
     */
    public long countSignedInDayOfMonth(String userId) {
        String signKey = this.signKeyWitMouth(userId);
        return this.bitCount(signKey);
    }

    /**
     * 查询用户当月连续签到次数
     *
     * @param userId
     * @return
     */
    public long countContinuousSignOfMonth(String userId) {
        String signKey = this.signKeyWitMouth(userId);
        int dayOfMonth = MonthDay.now().getDayOfMonth();
        List<Long> bitfield = this.bitfield(signKey, dayOfMonth, 0);
        int signCount = 0;
        if(CollectionUtils.isEmpty(bitfield)){
            return signCount;
        }
        long signFlag = Optional.ofNullable(bitfield.get(0)).orElse(0L);
        // 连续不为0即为连续签到次数，当天未签到情况下
        for (int i = dayOfMonth; i > 0; i--) {
            if (signFlag >> 1 << 1 == signFlag) {
                if (i != dayOfMonth) {
                    break;
                }
            } else {
                signCount += 1;
            }
            signFlag >>= 1;
        }
        return signCount;
    }

}
