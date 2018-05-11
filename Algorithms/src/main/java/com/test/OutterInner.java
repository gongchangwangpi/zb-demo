package com.test;

/**
 * Created by books on 2017/4/19.
 */
public class OutterInner {

    public void t() {
        Inner inner = new Inner();
        
    }

    public class Inner {

    }

    public static void main(String[] args) {

        OutterInner o = new OutterInner();

//        Inner inner = new OutterInner.Inner();
//         new OutterInner().Inner();

    }
}
