package com.middlesoftware.dubbo.test.serialize;

import com.alibaba.com.caucho.hessian.io.HessianSerializerInput;
import com.alibaba.com.caucho.hessian.io.HessianSerializerOutput;
import com.alibaba.com.caucho.hessian.io.JavaDeserializer;
import com.alibaba.com.caucho.hessian.io.JavaSerializer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author zhangbo
 */
public class Test {

    private static final String fileName = "F://son";
    
    public static void main(String[] args) throws IOException {

        writeObj();

        readObj();

    }

    private static void readObj() throws IOException {
        JavaDeserializer javaDeserializer = new JavaDeserializer(Son.class);
        HessianSerializerInput input = new HessianSerializerInput(new FileInputStream(fileName));
//        Object obj = javaDeserializer.readObject(input);
        Object obj = javaDeserializer.readMap(input);
//        System.out.println("read code = " + son.getCode());
        System.out.println(obj);
    }

    private static void writeObj() throws IOException {
        Son son = new Son();
        son.setId(1L);
        son.setName("son");
        son.setCode("soncode");

        Parent parent = son;
        System.out.println(parent.getCode());

        HessianSerializerOutput output = null;
        try {
            JavaSerializer javaSerializer = new JavaSerializer(Parent.class, Test.class.getClassLoader());
            output = new HessianSerializerOutput(new FileOutputStream(fileName));

            javaSerializer.writeObject(son, output);
        } finally {
            if (output != null) {
                output.close();
            }
        }
    }

}
