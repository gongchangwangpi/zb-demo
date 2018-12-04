package com.jdksource.java8;

import com.google.common.base.Joiner;

import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * @author zhangbo
 */
public class MapForeachTest {

    public static void main(String[] args) {
        
        int count = 100_00000;
        
        Map<String, String> map = new TreeMap<>();
        map.put("templateId", "CX001");
        map.put("memberId", "296109486844674048");
        map.put("priority", "9999");
        map.put("content", "{\"vehiclePlate\":\"川A12345\"}");
        map.put("urlParams", "{\"orderCode\":\"20181204152201456789\"}");
        
        String s1 = "";
        long b1 = System.nanoTime();
        for (int i = 0; i < count; i++) {
            s1 = "";
            for (Map.Entry<String, String> entry : map.entrySet()){
                s1 += entry.getKey() + "=" + entry.getValue();
            }
        }
        long b2 = System.nanoTime();
        System.out.println(b2 - b1);
        System.out.println(s1);

        String s2 = "";
        b1 = System.nanoTime();
        for (int i = 0; i < count; i++) {
           s2 = Joiner.on("").withKeyValueSeparator("=").join(map);
        }
        b2 = System.nanoTime();
        System.out.println(b2 - b1);
        System.out.println(s2);
        
        String s3 = "";
        b1 = System.nanoTime();
        for (int i = 0; i < count; i++) {
           s3 = map.entrySet().stream().map(entry -> entry.getKey() + "=" + entry.getValue()).collect(Collectors.joining());
        }
        b2 = System.nanoTime();
        System.out.println(b2 - b1);
        System.out.println(s3);
        
    }
    
}
