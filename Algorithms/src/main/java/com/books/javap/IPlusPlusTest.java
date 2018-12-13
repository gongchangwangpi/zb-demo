package com.books.javap;

/**
 * javap反编译查看字节码
 * 
 * @author zhangbo
 */
public class IPlusPlusTest {

    public static void main(String[] args) {
        int i = 1;
        // iinc 自增指令
        i++;
        System.out.println(i);
        // iinc 自增指令
        ++i;
        System.out.println(i);
    }
    
}
