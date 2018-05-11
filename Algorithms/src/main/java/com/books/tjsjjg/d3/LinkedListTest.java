package com.books.tjsjjg.d3;

import java.util.LinkedList;

/**
 * Created by Administrator on 2017/7/10 0010.
 */
public class LinkedListTest {

    public static void main(String[] args) {
        LinkedList<String> linkedList = new LinkedList<>();

        linkedList.add("1");
        linkedList.add("2");
        linkedList.add("3");

        System.out.println(linkedList);

        linkedList.set(2, "22");
        System.out.println(linkedList);
    }

}
