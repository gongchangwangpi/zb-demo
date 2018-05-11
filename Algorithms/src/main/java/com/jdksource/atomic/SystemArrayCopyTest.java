package com.jdksource.atomic;

import java.util.Arrays;

/**
 * 数组copy测试
 *
 * 只是数组copy了，但里面的元素，还是之前数组的引用，元素没有变
 *
 * Created by books on 2017/3/22.
 */
public class SystemArrayCopyTest {

    public static void main(String[] args) {

        SystemArrayCopyTest s1 = new SystemArrayCopyTest();
        SystemArrayCopyTest s2 = new SystemArrayCopyTest();
        SystemArrayCopyTest s3 = new SystemArrayCopyTest();

        Object[] o = new Object[]{s1, s2, s3};
        Object[] dest = new Object[3];

        System.arraycopy(o, 0, dest, 0, 3);

        System.out.println(Arrays.toString(o));
        System.out.println(Arrays.toString(dest));
       // [com.jdksource.atomic.SystemArrayCopyTest@79032d84, com.jdksource.atomic.SystemArrayCopyTest@3fa1732d, com.jdksource.atomic.SystemArrayCopyTest@90affe]
       // [com.jdksource.atomic.SystemArrayCopyTest@79032d84, com.jdksource.atomic.SystemArrayCopyTest@3fa1732d, com.jdksource.atomic.SystemArrayCopyTest@90affe]

    }

}
