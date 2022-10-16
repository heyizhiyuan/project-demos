package com.cnj.demo.bloomfilter;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.nio.charset.StandardCharsets;

/**
 * @author czz
 * @since 2022/10/15 下午8:14
 */
public class BloomFilterTest {

    public static void main(String[] args) {
        // 初始化布隆过滤器，预计统计元素数量为55000000，期望误差率为0.03
        BloomFilter<CharSequence> bloomFilter = BloomFilter.create(Funnels.stringFunnel(StandardCharsets.UTF_8), 55000000, 0.03);
        bloomFilter.put("value1");
        bloomFilter.put("value2");
        System.out.println("BloomFilterTest.contains value2: " + bloomFilter.mightContain("value2"));

        System.out.println("BloomFilterTest.contains value3: " + bloomFilter.mightContain("value3"));
    }

}
