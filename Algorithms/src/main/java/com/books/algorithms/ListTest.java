package com.books.algorithms;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by books on 2017/3/16.
 */
public class ListTest {

    public static void main(String[] args) {

        List<String> l1 = new ArrayList<>();
        List<String> l2 = new ArrayList<>();

        l1.add("11");
        l1.add("22");
        l1.add("33");
        l1.add("44");

        l2.add("33");
        l2.add("44");
        l2.add("55");

//        boolean b = l1.removeAll(l2);
//        boolean b = l1.removeAll(l1);
//        boolean b = l1.retainAll(l2);

//        System.out.println(b);
//        System.out.println(l1);
//        System.out.println(l2);


        List<String> subList = l1.subList(2, 4);
//        System.out.println(subList);

        /*int i = 8;

        System.out.println(17 >> 1);*/

        List<String> l3 = new LinkedList<>();
        l3.add(null);
        l3.add("22");
        l3.add(null);
        l3.add("44");

//        Object[] objects = l3.toArray();
//        System.out.println(Arrays.toString(objects));

//        Integer[] integers = l3.toArray(new Integer[]{});

//        System.out.println(9 >> 2);

//        System.out.println(Runtime.getRuntime().availableProcessors());

        String s = l3.get(2);
    }



}
