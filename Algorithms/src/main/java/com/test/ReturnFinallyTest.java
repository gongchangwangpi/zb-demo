package com.test;

/**
 * @author zhangbo
 * @date 2019-10-08
 */
public class ReturnFinallyTest {

    private String s;

    public static void main(String[] args) {

        ReturnFinallyTest test = new ReturnFinallyTest();

        String s = test.test();

        System.out.println(s);

        System.out.println(test.s);

    }

    private String test() {
        try {
            return r();
        } finally {
            f();
        }
    }


    private String r() {
        System.out.println("----- r");
        this.s = "r";
        return this.s;
    }

    private String f() {
        System.out.println("----- f");
        System.out.println("this.s = " + this.s);
        this.s = "f";
        return "f";
    }

}
