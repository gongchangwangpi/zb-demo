package com.guava.base;

import com.google.common.base.CaseFormat;

/**
 * 风格转换：下划线<-->驼峰<-->大小写等
 *
 * @author zhangbo
 * @date 2020/4/29
 */
public class CaseFormatTest {

    public static void main(String[] args) {

        String userName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, "user_name");
        System.out.println(userName);

        userName = CaseFormat.LOWER_CAMEL.converterTo(CaseFormat.UPPER_UNDERSCORE).convert(userName);
        System.out.println(userName);

    }

}
