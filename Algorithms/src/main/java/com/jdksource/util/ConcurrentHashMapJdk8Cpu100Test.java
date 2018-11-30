package com.jdksource.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhangbo
 */
public class ConcurrentHashMapJdk8Cpu100Test {

    public static void main(String[] args) {

        Map<String, String> map = new ConcurrentHashMap<>();
        
        map.computeIfAbsent("AaAa",
                key -> map.
                        computeIfAbsent("BBBB", key2 -> "value"));
        
    }
    
}
