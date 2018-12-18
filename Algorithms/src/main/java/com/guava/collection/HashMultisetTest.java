package com.guava.collection;

import com.google.common.collect.HashMultiset;

/**
 * @author zhangbo
 */
public class HashMultisetTest {

    public static void main(String[] args) {

        HashMultiset<String> multiset = HashMultiset.create();

        multiset.add("1");
        multiset.add("1");
        multiset.add("2");
        multiset.add("1");
        
        System.out.println(multiset);
        System.out.println(multiset.count("1"));
        System.out.println(multiset.size());

    }
    
}
