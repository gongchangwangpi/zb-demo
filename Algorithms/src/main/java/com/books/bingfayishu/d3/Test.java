package com.books.bingfayishu.d3;

/**
 * @author zhangbo
 */
public class Test {
    
    private static int i = 0;
    
    private static volatile boolean flag = false;
    
    private static void writer() {
        i = 1;
        flag = true;
    }
    
    private static void reader() {
        if (flag) {
            i = 1;
            System.out.println("flag = true");
        } else {
            System.out.println("false");
        }
    }

    public static void main(String[] args) {
        reader();
        writer();
        reader();
    }
}
