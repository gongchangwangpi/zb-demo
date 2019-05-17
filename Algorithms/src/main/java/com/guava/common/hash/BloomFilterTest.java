package com.guava.common.hash;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.nio.charset.Charset;

/**
 * @author zhangbo
 */
public class BloomFilterTest {

    public static void main(String[] args) {

        BloomFilter<String> bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charset.forName("UTF-8")), 100_000);

        for (int i = 0; i < 10000; i++) {
            bloomFilter.put(String.valueOf(i));
        }

        boolean contain = bloomFilter.mightContain("1000011");
        System.out.println(contain);

    }
    
}
