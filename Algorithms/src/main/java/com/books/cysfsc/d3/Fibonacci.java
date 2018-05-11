package com.books.cysfsc.d3;

import java.util.Arrays;

/**
 * 斐波那契数列
 * 
 * 一对兔子，每个月新生一对兔子，新生兔子在2个月后(第三个月)的时候也会新生一对兔子
 * 第一个月：1对
 * 第二个月：1对
 * 第三个月：2对 新生1
 * 第四个月：3对 再新生1
 * 第五个月：5对 老兔子新生1，新生兔子新生1
 * 第六个月：8对
 * 
 * 即从第三个月开始，后面每个月的数量为前面两个月数量的和
 * 
 * Fn = Fn-1 + Fn-2
 * 
 * 
 * @author zhangbo
 */
public class Fibonacci {

    public static void main(String[] args) {

        int result = fibonacci(15);

        System.out.println(result);

        int[] arr = fibonacci2(15);
        System.out.println(Arrays.toString(arr));
    }
    
    private static int fibonacci(int month) {
        if (month == 1 || month == 2) {
            return 1;
        }
        int fibonacciN_1 = fibonacci(month - 1);
        int fibonacciN_2 = fibonacci(month - 2);
        return fibonacciN_1 + fibonacciN_2;
    }
    
    private static int[] fibonacci2(int month) {
        if (month < 1) {
            throw new IllegalArgumentException("must greater than 1");
        }
        int[] result = new int[month];
        if (month == 1) {
            result[0] = 1;
            return result;
        }
        result[0] = result[1] = 1;
        if (month == 2) {
            return result;
        }
        for (int i = 2; i < month; i++) {
            result[i] = result[i - 1] + result[i - 2];
        }
        
        return result;
    }
    
}
