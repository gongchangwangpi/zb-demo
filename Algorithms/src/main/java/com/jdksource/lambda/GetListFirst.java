package com.jdksource.lambda;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangbo
 */
public class GetListFirst {

    public static void main(String[] args) {

        List<String> list = new ArrayList<>();
        list.add("11");
        list.add("12");
        list.add("13");

        String first = list.stream().findFirst().orElseGet(() -> "default");

        System.out.println(first);
    }
    
}
