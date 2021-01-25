//package com.jdksource;
//
//import com.zb.demo.util.unsafe.UnsafeUtil;
//import sun.misc.Contended;
//import sun.misc.Unsafe;
//
//import java.lang.reflect.Field;
//
///**
// * -XX:-RestrictContended
// *
// * 会填充128byte来避免CPU伪共享
// *
// * @author zhangbo
// */
//public class ContentedTest {
//
//    @Contended("a")
//    private int value1;
//
//    @Contended("b")
//    private long value2;
//
//    @Contended("b")
//    private long value3;
//
//    public static void main(String[] args) throws NoSuchFieldException {
//
//        Unsafe unsafe = UnsafeUtil.getUnsafe();
//
//        Field field1 = ContentedTest.class.getDeclaredField("value1");
//        long offset1 = unsafe.objectFieldOffset(field1);
//        System.out.println(offset1);
//
//        Field field2 = ContentedTest.class.getDeclaredField("value2");
//        long offset2 = unsafe.objectFieldOffset(field2);
//        System.out.println(offset2);
//
//        Field field3 = ContentedTest.class.getDeclaredField("value3");
//        long offset3 = unsafe.objectFieldOffset(field3);
//        System.out.println(offset3);
//
//    }
//
//}
