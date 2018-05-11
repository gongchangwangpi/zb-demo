package com.books.jvm.d8;

/**
 * Created by Administrator on 2017/5/8 0008.
 */
public class Dispatch {

    static class C {}
    
    static class QQ extends C {}

    static class _360 extends C {}

    static class Father {
        public void choose (QQ qq) {
            System.out.println("Father choose QQ");
        }
        public void choose (_360 _360) {
            System.out.println("Father choose 360");
        }
        public void choose (C c) {
            System.out.println("Father choose c");
        }
    }

    static class Son extends Father {
        public void choose (QQ qq) {
            System.out.println("Son choose QQ");
        }
        public void choose (_360 _360) {
            System.out.println("Son choose 360");
        }
        public void choose (C c) {
            System.out.println("Son choose c");
        }
    }

    public static void main(String[] args) {
        Father father = new Father();
        Father son = new Son();
        father.choose(new _360());
        son.choose(new QQ());
        son.choose((C) new QQ());
    }
}
