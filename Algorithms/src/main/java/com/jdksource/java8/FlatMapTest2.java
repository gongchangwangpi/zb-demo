package com.jdksource.java8;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * @author zhangbo
 */
public class FlatMapTest2 {

    public static void main(String[] args) {

        List<Integer> list1 = Arrays.asList(1, 2, 3);
        List<Integer> list2 = Arrays.asList(4, 5);

        List<List<Integer>> list = new ArrayList<>();
        list.add(list1);
        list.add(list2);


        List<Integer> collect = list.stream().flatMap(l -> {
            return l.stream();
        }).collect(toList());

        System.out.println(JSON.toJSONString(collect));

    }
    
}
