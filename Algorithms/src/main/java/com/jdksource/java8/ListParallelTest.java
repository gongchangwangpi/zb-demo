package com.jdksource.java8;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangbo
 */
public class ListParallelTest {

    public static void main(String[] args) {

        List<String> list = new ArrayList<>();
        
        for (int i = 0; i < 100; i++) {
            list.add(String.valueOf(Math.random() * i));
        }
        
        list.parallelStream().forEach(s -> {
            System.out.println(Thread.currentThread());
        });
        
    }
    
}
