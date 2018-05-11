package com.jdksource.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author zhangbo
 */
public class HashMapTest {

    public static void main(String[] args) {
        
        Map<String, String> map = new HashMap<>();
        
        map.put("1", "1");

        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        
        map.put("2", "2");
        
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        
    }
    
}
