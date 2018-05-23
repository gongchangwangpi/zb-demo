package com.guava.collection;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.BoundType;
import com.google.common.collect.SortedMultiset;
import com.google.common.collect.TreeMultiset;

/**
 * @author zhangbo
 */
public class TreeMultisetTest {

    public static void main(String[] args) {

        List<Integer> list = new ArrayList<>(100);
        for (int i = 0; i < 100; i++) {
            list.add(i);
        }
        
        TreeMultiset<Comparable> treeMultiset = TreeMultiset.create();
        treeMultiset.addAll(list);
        System.out.println(treeMultiset);

        SortedMultiset<Comparable> headMultiset = treeMultiset.headMultiset(25, BoundType.CLOSED);
        System.out.println(JSON.toJSONString(headMultiset));

        SortedMultiset<Comparable> tailMultiset = treeMultiset.tailMultiset(90, BoundType.OPEN);
        System.out.println(JSON.toJSONString(tailMultiset));

        SortedMultiset<Comparable> subMultiset = treeMultiset.subMultiset(50, BoundType.CLOSED, 60, BoundType.OPEN);
        System.out.println(subMultiset);
    }
    
}
