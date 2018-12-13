package com.guava.collection;

import com.google.common.collect.HashMultimap;

import java.util.Set;

/**
 * @author zhangbo
 */
public class HashMultimapTest {

    public static void main(String[] args) {

        HashMultimap<String, String> multimap = HashMultimap.create();
        
        multimap.put("1", "11");
        multimap.put("1", "12");
        boolean put = multimap.put("1", "13");
        System.out.println(put);
        put = multimap.put("1", "13");
        System.out.println(put);

        multimap.put("2", "21");
        multimap.put("2", "22");
        
        multimap.put("3", "31");

        System.out.println(multimap);
        // 本质上一个Set在存储value
        Set<String> values = multimap.get("1");

        System.out.println(values);

    }
    
}
