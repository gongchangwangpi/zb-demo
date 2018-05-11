package com.jdksource.util.concurrent;

import java.util.Arrays;

/**
 * @author zhangbo
 */
public class VolatileIntArrayTest {
    
    private static volatile int[] arr = {1,2,3,4,5};

    public static void main(String[] args) {
        Thread writer = new Thread(new Writer(5));
        writer.start();

        Thread reader = new Thread(new Reader());
        reader.start();

    }
    
    static class Reader implements Runnable {

        @Override
        public void run() {
            System.out.println(Arrays.toString(arr));
        }
    }
    
    static class Writer implements Runnable {
        int i;

        public Writer(int i) {
            this.i = i;
        }
        
        @Override
        public void run() {
            for (int j = i; j < i + 5; j++) {
                arr[j - i] = j;
            }    
        }
    }
}
