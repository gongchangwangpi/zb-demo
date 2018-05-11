package com.books.gaobingfa.d4;

/**
 * Created by books on 2017/4/27.
 */
public class ThreadLocalTest {


    public static void main(String[] args) {

        ThreadLocal<String> local1 = new ThreadLocal<>();
        local1.set("1");
        System.out.println(local1.get());
        local1.set("2");

        System.out.println(local1.get());

        int[] i = new int[-1];
    }

}
