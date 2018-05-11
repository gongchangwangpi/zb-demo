package com.test;

import sun.security.action.GetPropertyAction;

import java.nio.charset.Charset;
import java.security.AccessController;

/**
 * 默认字符集，默认采用操作系统的(win下默认GBK,Linux默认UTF-8)
 *
 * 如果有设置JVM启动参数 file.encoding 的，则采用设置的
 *
 * Created by books on 2017/4/10.
 */
public class DefaultCharsetTest {

    public static void main(String[] args) {

        Charset charset = Charset.defaultCharset();

        System.out.println(charset);

        String csn = AccessController.doPrivileged(
                new GetPropertyAction("file.encoding"));

        System.out.println(csn);

    }

}
