package com.jdksource.util;

import java.util.LinkedHashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

/**
 * 使用 LinkedHashMap 实现LRU的缓存
 * 
 * 重写 boolean removeEldestEntry(Map.Entry eldest)方法，选择适当的逻辑
 * 在构造LinkedHashMap时，可以选择按 access-order（访问排序，可以实现LRU）
 * 还是 insertion-order（插入排序）
 * 
 * @author zhangbo
 */
@Slf4j
public class LinkedHashMapLruTest {


    public static void main(String[] args) {

        Map<String, Object> map = new LinkedHashMap<String, Object>(4, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
                log.info(JSON.toJSONString(eldest));
                return size() > 4;
            }
        };
        
        System.out.println(map.put("1", "1"));
        System.out.println(map.put("2", "1"));
        System.out.println(map.put("3", "1"));
        System.out.println(map.put("4", "1"));

        map.get("1");

        System.out.println(map.put("5", "1"));
        System.out.println(map.put("6", "1"));

        System.out.println(JSON.toJSONString(map));
        
    }
    
}
