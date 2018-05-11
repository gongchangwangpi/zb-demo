package com.util;

/**
 * 数字处理工具类
 * 
 * Created by books on 2017/11/15.
 */
public class NumberUtil {
    
    private NumberUtil() {
    }

    /**
     * 包装类转换为原生类型
     *
     * @param number
     * @param defaultValue
     * @return
     */
    public static int toInt(Number number, int defaultValue) {
        return number == null ? defaultValue : number.intValue();
    }

    public static int toInt(Number number) {
        if (number == null) {
            throw new IllegalArgumentException("parameter number must not be null");
        }
        return number.intValue();
    }
    
    public static long toLong(Number number, long defaultValue) {
        return number == null ? defaultValue : number.longValue();
    }

    /**
     * 求和
     * <p>
     *     如有参数为 null, 默认按 0 处理
     * </p>
     *
     * @param integers
     * @return
     */
    public static int sum(Integer... integers) {
        if (integers == null || integers.length == 0) {
            return 0;
        }
        int sum = 0;
        for (Integer i : integers) {
            sum += toInt(i, 0);
        }
        return sum;
    }

    public static void main(String[] args) {
        Integer i1 = 10;
        Integer i2 = null;
        int i3 = 5;
        System.out.println(sum(i1, i2, i3, null, 3));
        System.out.println(sum(null, null));
        System.out.println(sum());
    }
}
