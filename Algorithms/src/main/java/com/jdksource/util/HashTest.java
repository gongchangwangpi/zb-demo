package com.jdksource.util;

/**
 * @author zhangbo
 * @date 2020/12/5
 */
public class HashTest {

    public static void main(String[] args) {

        // 1.取key本身的hashCode
        // 2.将hashCode左移16位，得到hashCode的高16位
        // 3.在将1和2的结果异或(相同为0，不同为1)得到最终的hash值
        // 4.按capacity-1，在与运算(取低位的4/8位等)，得到最终的数组的索引


        String key = "123342fdsfesgrsdfgeEsrgsfdgsgrsj";
//        String key = "abc";

        int h = key.hashCode();

//        System.out.println("hashCode = \t" + h);
        System.out.println("hashCode byte = \t" + Integer.toBinaryString(h));

        int i = h >> 16;
//        System.out.println("hashCode >> 16 = \t" + i);
        System.out.println("hashCode >> 16 高16位 byte = \t" + Integer.toBinaryString(i));

        int hash = h ^ i;
//        System.out.println("最终hash = \t" + hash);
        System.out.println("最终异或hash byte = \t" + Integer.toBinaryString(hash));

        int c = 15;
        System.out.println("capacity = \t" + Integer.toBinaryString(c));

        int index = c & hash;
//        System.out.println("index = \t" + index);
        System.out.println("index byte = \t" + Integer.toBinaryString(index));

    }

}
