package com.books.javap;

/**
 * @author zhangbo
 */
public class TryCatchFinallyTest {

    public static void main(String[] args) {
        try {
            int i = 1 / 0;
        } catch (Exception e) {
            System.out.println("Exception");
        } finally {
            System.out.println("Finally");
        }
    }
    
}
