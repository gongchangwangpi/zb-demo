package com.jdksource.util;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 使用 LinkedHashMap 
 * 
 * @author zhangbo
 */
@Slf4j
public class LinkedHashMapTest {


    public static void main(String[] args) {
        
        // 链表保证有序,在{@link LinkedHashMap#newNode}中使用before/after双向链表
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        
        map.put("1", "1");
        map.put("3", "3");
        map.put("2", "2");
        map.put("4", "4");

        for (Map.Entry<String, Object> entry : map.entrySet()){
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }

        // 红黑树保证有序(此时的有序不是插入顺序，而是Comparable或者Comparator维护的比较之后的顺序)
        TreeMap<String, Object> treeMap = new TreeMap<>();

        treeMap.put("1", "1");
        treeMap.put("3", "3");
        treeMap.put("2", "2");
        treeMap.put("4", "4");

        for (Map.Entry<String, Object> entry : treeMap.entrySet()){
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }
        
    }
    
}
