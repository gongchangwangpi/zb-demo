package com.books.cysfsc.d3;

/**
 * 阶乘
 * 
 * @author zhangbo
 */
public class FactTest {

    public static void main(String[] args) {

        long fact = fact(10);
        System.out.println(fact);

    }
    
    private static long fact(long n) {
        if (n <= 1) {
            return 1;
        }
        return n * fact(n - 1);
    }
}
