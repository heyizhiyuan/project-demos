package com.cnj.demo.bloomfilter;

import org.redisson.Redisson;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * @author czz
 * @since 2022/10/15 下午8:14
 */
public class BloomFilterTest {

    public static RedissonClient createRedisson() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://47.104.230.2:6390")
                .setPassword("123");
        return Redisson.create(config);
    }

    public static void main(String[] args) {
        RBloomFilter<String> bloomFilter = createRedisson().getBloomFilter("sample");
        // 初始化布隆过滤器，预计统计元素数量为55000000，期望误差率为0.03
        bloomFilter.tryInit(55000000L, 0.03);
        bloomFilter.add("value1");
        bloomFilter.add("value2");
        System.out.println("BloomFilterTest.contains value2: " + bloomFilter.contains("value2"));

        System.out.println("BloomFilterTest.contains value3: " + bloomFilter.contains("value3"));
    }

}
