package com.jdksource.jvm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * -Xmx1024M -Xms1024M -Xmn256M -XX:MetaspaceSize=50M -XX:MaxMetaspaceSize=100M -XX:+PrintGCDetails
 * -XX:+PrintGCDateStamps -XX:+PrintHeapAtGC -XX:+UseConcMarkSweepGC
 * <p>
 * 频繁生成类加载器加载少量类，会造成metaspace内存碎片，导致FGC
 *
 * @author zhangbo
 * @date 2019-10-15
 */
public class MetaspaceFGCTest {

    public static void main(String[] args) throws ClassNotFoundException {

        while (true) {
            new MyClassLoader().findClass("com.jdksource.jvm.MyClassLoader");
        }

    }

}


class MyClassLoader extends ClassLoader {

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        try {

            String filePath = "/Users/zhangbo/IdeaProjects/zb-demo/Algorithms/target/classes/" + name.replace('.', File.separatorChar) + ".class";

            //指定读取磁盘上的某个文件夹下的.class文件：

            File file = new File(filePath);

            FileInputStream fis = new FileInputStream(file);

            byte[] bytes = new byte[fis.available()];
            fis.read(bytes);

            //调用defineClass方法，将字节数组转换成Class对象

            Class<?> clazz = this.defineClass(name, bytes, 0, bytes.length);
            fis.close();

            return clazz;

        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

        } finally {

        }

        return super.findClass(name);
    }

}
