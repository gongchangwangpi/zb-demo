package com.jdksource.io;

import lombok.Data;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 
 * 父类没有实现{@link Serializable}，子类实现{@link Serializable}，
 * 则序列化时，父类属性不会序列化
 * 
 * @author zhangbo
 */
public class SerializeTest {

    public static void main(String[] args) throws Exception {
        Sub sub = new Sub();
        sub.setAge(10);
        sub.setName("sub");

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(sub);

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Sub o = (Sub) objectInputStream.readObject();
        
        System.out.println(o.getAge());
        System.out.println(o.getName());
    }
    
    @Data
    private static class Super /*implements Serializable*/ {
        protected String name;
    }
    @Data
    private static class Sub extends Super implements Serializable {
        private static final long serialVersionUID = -1272542789787628016L;
        private int age;
    }
}
