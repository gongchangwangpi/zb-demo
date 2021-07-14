package com.jdksource.lang;

import com.bedpotato.alg4.utils.In;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangbo
 */
@Slf4j
public class ThreadLocalReSizeTest1 {


    public static void main(String[] args) {

        List<ThreadLocal<Integer>> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            ThreadLocal<Integer> threadLocal = new ThreadLocal<>();
            threadLocal.set(i);
            list.add(threadLocal);
        }

        System.out.println(list.get(10).get());

    }
    
}
