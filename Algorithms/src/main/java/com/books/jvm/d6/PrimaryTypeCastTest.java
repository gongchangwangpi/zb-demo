package com.books.jvm.d6;

/**
 * 基本类型转换
 * 高位向低位转换时，直接丢弃多余低位的位数
 *
 * Created by Administrator on 2017/4/26 0026.
 */
public class PrimaryTypeCastTest {

    public static void main(String[] args) {
        byte b = 1;
        short s = 1;
        int i = 1;
        long l = 1;
        char c = 'c';
        float f = 1;
        double d = 1;

        float fi = i;

        long l1 = 1000100010001000L;
        int i1 = (int) l1;
        System.out.println(i1);
        // 11100011011001010111101101110101100000001001101000
        //                   11101101110101100000001001101000
        System.out.println(Long.toBinaryString(l1));
        System.out.println(Integer.toBinaryString(i1));

        double d1 = 12345678901234567890123456789012345678901234567890D;
        int i2 = (int) d1;
        // 2147483647
        // 如果超出了int / long 的表示范围，则转换为int / long 能表示的最大或最小正整数
        System.out.println(i2);

        double d2 = 55.69;
        int i3 = (int) d2;
        // 55 根据IEEE754的向零舍入模式取整
        System.out.println(i3);

        double d3 = 1 / 0.0;
        int i4 = (int) d3;
        // 2147483647
        System.out.println(i4);

        System.out.println("--------------------------------------");

        float f1 = (float) d1;
        // Infinity 如果转换结果太大，则返回float的(正/负)无穷大
        System.out.println(f1);

        double d4 = 0.00000000000000000000000000000000000000000000000000000000000001;
        float f2 = (float) d4;
        // 0.0 如果转换结果太小，则返回float的(正/负)零
        System.out.println(f2);

        // true
        System.out.println(Float.POSITIVE_INFINITY == Double.POSITIVE_INFINITY);
        // true
        System.out.println(Float.NEGATIVE_INFINITY == Double.NEGATIVE_INFINITY);
        // false
        System.out.println(Float.NaN == Double.NaN);
    }

}
