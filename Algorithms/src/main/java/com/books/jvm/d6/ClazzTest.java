package com.books.jvm.d6;

/**
 * 字节码
 *
 * Created by Administrator on 2017/4/24 0024.
 */
public class ClazzTest {

    private String str = "xxx";

    protected int i;

    public static final String NAME = "name";

    public String getName() {
        return "name";
    }

    public static void main(String[] args) {
        ClazzTest test = new ClazzTest();
        String name = test.getName();

        System.out.println(name);
    }
}
