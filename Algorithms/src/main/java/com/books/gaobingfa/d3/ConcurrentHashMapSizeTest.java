package com.books.gaobingfa.d3;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by books on 2017/4/27.
 */
public class ConcurrentHashMapSizeTest {

    public static void main(String[] args) {

        Map<Integer, Integer> map = new ConcurrentHashMap<>();

        map.put(1, 1);

        int size = map.size();

        System.out.println(size);

    }

}
