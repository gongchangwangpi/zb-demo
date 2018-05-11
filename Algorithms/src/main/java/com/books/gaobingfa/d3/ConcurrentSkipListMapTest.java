package com.books.gaobingfa.d3;

import java.util.HashMap;
import java.util.Map;

/**
 * 跳表Map
 *
 * Created by books on 2017/4/27.
 */
public class ConcurrentSkipListMapTest {

    public static void main(String[] args) {
//        Map<Integer, Integer> map = new ConcurrentSkipListMap<>();
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < 10; i++) {
            Integer put = map.put(i, i);
            System.out.println(put);
        }

        Integer put = map.put(5, 6);
        System.out.println(put);
    }

}
