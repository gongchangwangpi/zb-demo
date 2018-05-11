package com.test;

import java.io.*;

/**
 * 创建对象的方式
 * 及是否调用构造器
 *
 * 实验证明： 直接new对象或者用反射，会调用构造器
 *          用clone或者ObjectInputStream.readObject()不会调用构造器
 *
 *          还有数组对象，没有构造器调用
 *
 * Created by books on 2017/3/27.
 */
public class NewInstanceTest implements Cloneable, Serializable {

   /* public NewInstanceTest() {
        System.out.println("无参构造器");
    }*/

    public NewInstanceTest(String s) {
        System.out.println("有参构造器");
    }



    public static void main(String[] args) throws Exception {

        NewInstanceTest o1 = new NewInstanceTest("xx");

//        NewInstanceTest o2 = o1.getClass().newInstance();

        Object o3 = o1.clone();

        String filePath = "F://o4.obj";
        FileOutputStream fileOutputStream = new FileOutputStream(filePath);
        ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);
        out.writeObject(o1);

        FileInputStream fileInputStream = new FileInputStream(filePath);
        ObjectInputStream in = new ObjectInputStream(fileInputStream);
        Object o4 = in.readObject();

        System.out.println(System.identityHashCode(o1));
//        System.out.println(System.identityHashCode(o2));
        System.out.println(System.identityHashCode(o3));
        System.out.println(System.identityHashCode(o4));
//        构造器
//        构造器
//        2053061040
//        133479882
//        1423449306
//        944658362

    }

}
