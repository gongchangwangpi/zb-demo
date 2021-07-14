package com.jdksource.java8;

import com.google.common.collect.Lists;

import java.util.ArrayList;

/**
 * @author bo6.zhang
 * @date 2021/6/28
 */
public class StreamForeachReturnTest {

    public static void main(String[] args) {

        ArrayList<String> list = Lists.newArrayList("1", "2", "3", "4", "5");

        list.forEach(s -> {
            if ("3".equalsIgnoreCase(s)) {
                return;
            }
            System.out.println(s);
        });

    }

}
