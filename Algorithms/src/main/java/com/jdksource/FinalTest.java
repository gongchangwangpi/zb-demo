package com.jdksource;

/**
 * @author zhangbo
 */
public class FinalTest {

    public static void main(String[] args) {
        new Parent().say();
    }
    
    static final class Parent {
        public void say() {
            System.out.println("Parent");
        }
    }
    
}
