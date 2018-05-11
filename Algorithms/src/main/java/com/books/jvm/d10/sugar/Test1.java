package com.books.jvm.d10.sugar;

import java.util.Arrays;
import java.util.List;

/**
 * 语法糖，自动拆箱，装箱，foreach
 *
 * 与Test2.class文件比较
 *
 * Created by Administrator on 2017/5/18 0018.
 */
public class Test1 {

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3, 4);
        int sum = 0;
        for (int i: list) {
            sum += i;
        }
        System.out.println(sum);
    }

}
