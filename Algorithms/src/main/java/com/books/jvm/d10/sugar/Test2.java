package com.books.jvm.d10.sugar;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * 与Test1.class文件比较
 *
 * Created by Administrator on 2017/5/18 0018.
 */
public class Test2 {
    public static void main(String[] args) {
        List list = Arrays.asList(new Integer[]{Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3), Integer.valueOf(4)});
        int sum = 0;

        int i;
        for(Iterator var3 = list.iterator(); var3.hasNext(); sum += i) {
            i = ((Integer)var3.next()).intValue();
        }

        System.out.println(sum);
    }
}
