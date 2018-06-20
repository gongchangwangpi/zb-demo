package com.jdksource.nio.chapter5;

import java.nio.CharBuffer;

/**
 * @author zhangbo
 */
public class CharSeq {

    public static void main(String[] argv) {
        StringBuffer stringBuffer = new StringBuffer("Hello World");
        CharBuffer charBuffer = CharBuffer.allocate(20);
        CharSequence charSequence = "Hello World";
        //直接来源于String
        printCharSequence(charSequence);
        //来源于StringBuffer
        charSequence = stringBuffer;
        printCharSequence(charSequence);
        //更改StringBuffer
        stringBuffer.setLength(0);
        stringBuffer.append("Goodbye cruel world");
        //相同、 “不变的”CharSequence产生了不同的结果
        printCharSequence(charSequence);
        //从CharBuffer中导出CharSequence
        charSequence = charBuffer;
        charBuffer.put("xxxxxxxxxxxxxxxxxxxx");
        charBuffer.clear();
        charBuffer.put("Hello World");
        charBuffer.flip();
        printCharSequence(charSequence);
        charBuffer.mark();
        charBuffer.put("Seeya");
        charBuffer.reset();
        printCharSequence(charSequence);
        charBuffer.clear();
        printCharSequence(charSequence);
        //更改基础CharBuffer会反映在只读的CharSequence接口上
    }

    private static void printCharSequence(CharSequence cs) {
        System.out.println("length=" + cs.length() + ", content='" + cs.toString() + "'");
    }


}
