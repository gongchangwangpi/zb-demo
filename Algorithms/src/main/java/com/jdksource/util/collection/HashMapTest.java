package com.jdksource.util.collection;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *  HashMap
 *  允许包含 null的key和 null的value
 *
 * Created by books on 2017/3/22.
 */
public class HashMapTest {

    public static void main(String[] args) {

        Map<Integer, String> map = new HashMap<>();
        for( int i = 1; i< 17; i++) {
            String s = String.valueOf(i);
            map.put(i, s);
        }
        map.put(null, "1222");
        map.put(16, "1222");
        map.put(17, "1222");
        map.put(-1, "1222");

        System.out.println(map.get(null));
        System.out.println(map.containsKey(null));

        Set<Integer> keySet = map.keySet();



    }

}
