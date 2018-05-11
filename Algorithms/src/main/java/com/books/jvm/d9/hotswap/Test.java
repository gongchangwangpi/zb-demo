package com.books.jvm.d9.hotswap;

import java.io.FileInputStream;

/**
 * Created by Administrator on 2017/5/15 0015.
 */
public class Test {

    public static void main(String[] args) throws Exception {
        FileInputStream is = new FileInputStream("F:/test.class");
        byte[] bytes = new byte[is.available()];
        is.read(bytes);
        is.close();

        System.out.println(JavaClassExecuter.execute(bytes));

    }

}
