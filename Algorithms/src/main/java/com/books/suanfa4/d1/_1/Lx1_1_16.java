package com.books.suanfa4.d1._1;

/**
 * Created by Administrator on 2017/6/13 0013.
 */
public class Lx1_1_16 {

    public static void main(String[] args) {
        String s = exR1(6);
        System.out.println(s);
    }

    public static String exR1(int n) {
        if (n <= 0) {
            return "";
        }
        return exR1(n - 3) + n + exR1(n - 2) + n;
    }

}
