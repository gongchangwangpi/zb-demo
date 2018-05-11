package com.books.algorithms;

/**
 * Created by books on 2017/3/10.
 */
public class T {

    public static void main(String[] args) {

        System.out.println(incre(15000, 0.1, 5));

        System.out.println(Object[].class);
        System.out.println(Integer[].class);
        System.out.println(Integer.class);

    }

    public static double incre(double jishu, double shangfu, int nian) {
        if (nian <= 0) return jishu;
        return incre(jishu * (1 + shangfu), shangfu, nian -1);
    }

}
