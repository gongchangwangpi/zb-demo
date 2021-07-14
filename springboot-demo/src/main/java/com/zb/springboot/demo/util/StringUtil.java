package com.zb.springboot.demo.util;

/**
 * @author bo6.zhang
 * @date 2021/3/16
 */
public class StringUtil {

    private static boolean isNull(String s) {
        System.out.println(" === original isNull");
        return s == null;
    }

    public static boolean isEmpty(String s) {
        System.out.println(" === original isEmpty");
        return isNull(s) || "".equals(s);
    }

}
