package com.jdksource;

/**
 * @author zhangbo
 * @date 2020/8/3
 */
public class ExtendsTest {

    public static void main(String[] args) {
        YellowHuman yellowHuman = new YellowHuman();
        yellowHuman.say();;
    }

    static class Human {
        public void say() {
            System.out.println("Human say");
            this.hello();
        }
        public void hello() {
            System.out.println("Human hello");
        }
    }
    static class YellowHuman extends Human {
        @Override
        public void say() {
            System.out.println("Yellow say");
            super.say();
        }

        @Override
        public void hello() {
            System.out.println("Yellow hello");
        }
    }
}
