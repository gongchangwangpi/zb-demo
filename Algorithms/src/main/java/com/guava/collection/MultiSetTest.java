package com.guava.collection;

import java.util.Set;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

/**
 * HashMultiset 可以类比于JDK的 HashMap
 * 其中key为储存的元素，value为元素出现的次数count
 * 
 * @author zhangbo
 */
public class MultiSetTest {

    public static void main(String[] args) {

        HashMultiset<Object> hashMultiset = HashMultiset.create();
        
        hashMultiset.add("1");
        hashMultiset.add("2");
        hashMultiset.add("3");
        hashMultiset.add("4");
        hashMultiset.add("3");
        hashMultiset.add("1");
        
        // 设置元素的个数
        hashMultiset.setCount("2", 3);
        boolean s = hashMultiset.setCount("2", 1, 5);

        // 集合的总数，包含重复元素
        System.out.println("元素总数：\t" + hashMultiset.size());
        System.out.println("不重复元素总数：\t" + hashMultiset.elementSet().size());
        // 指定元素出现的次数
        System.out.println("元素1出现的次数：\t" + hashMultiset.count("1"));

        System.out.println("---------------------------");
        
        // entrySet 不包含重复元素
        Set<Multiset.Entry<Object>> entrySet = hashMultiset.entrySet();
        for (Multiset.Entry<Object> entry : entrySet) {
            System.out.println(entry.getElement() + "\t" + entry.getCount());
        }

        System.out.println("---------------------------");
        // 不包含重复元素
        Set<Object> elementSet = hashMultiset.elementSet();
        for (Object ele : elementSet) {
            System.out.println(ele);
        }
    }
    
}
