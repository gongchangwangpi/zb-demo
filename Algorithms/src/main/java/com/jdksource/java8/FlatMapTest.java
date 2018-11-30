package com.jdksource.java8;

import com.alibaba.fastjson.JSON;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author zhangbo
 */
public class FlatMapTest {

    public static void main(String[] args) {

        List<Integer> list1 = Arrays.asList(1, 2, 3);
        List<Integer> list2 = Arrays.asList(3, 4);

        List<int[]> list = list1.stream()
                .flatMap(i -> list2.stream().map(j -> new int[]{i, j}))
                .filter((arr) -> (arr[0] + arr[1]) % 3 == 0)
                .collect(toList());

        System.out.println(JSON.toJSONString(list));

    }
    
}
