package com.jdksource.lang;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangbo
 * @date 2019-10-31
 */
public class ProcessTestDemo {

    public static void main(String[] args) {
        while (true) {
            System.out.println(new Date());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
