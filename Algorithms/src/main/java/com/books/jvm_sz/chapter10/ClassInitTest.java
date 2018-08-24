package com.books.jvm_sz.chapter10;

/**
 * @author zhangbo
 */
public class ClassInitTest {

    static {
        s = "22";
        // class init由类中的static字段和static代码块构成，
        // 顺序为在类中声明的顺序，此处s变量在static代码块中仅可以进行赋值，不能使用
//        System.out.println(s);
    }
    
    static String s;
    
    public static void main(String[] args) {
        
    }
    
}
