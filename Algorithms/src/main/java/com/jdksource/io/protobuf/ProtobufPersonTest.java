package com.jdksource.io.protobuf;

import com.alibaba.fastjson.JSON;
import com.google.protobuf.InvalidProtocolBufferException;

import java.util.Arrays;

/**
 * 命令行生成Java class
 *
 * protoc.exe --java_out=D:\IdeaProjects\github\zb-demo\Algorithms\src\main\java
 * -I=D:\IdeaProjects\github\zb-demo\Algorithms\src\main\java\com\jdksource\io\protobuf ProtobufPerson.proto
 *
 * @author bo6.zhang
 * @date 2021/2/19
 */
public class ProtobufPersonTest {

    public static void main(String[] args) throws InvalidProtocolBufferException {

        // builder构建对象
        ProtobufPerson.Person person = ProtobufPerson.Person.newBuilder()
                .setId(111L)
                .setName("name123你好中国")
                .setAge(18)
                .build();

        System.out.println(JSON.toJSONString(person));

        // 序列化为byte[]
        byte[] bytes = person.toByteArray();
        System.out.println(bytes.length);
        System.out.println(JSON.toJSONString(bytes));
        System.out.println(Arrays.toString(bytes));

//        System.out.println(person.toString());
//        System.out.println(person.toByteString());

        // 反序列化
        ProtobufPerson.Person parse = ProtobufPerson.Person.parseFrom(bytes);

        // 序列化和反序列化时，写入字节的长度
//        person.writeDelimitedTo();
//        ProtobufPerson.Person.parseDelimitedFrom();

        // false
        System.out.println("parse == person: " + (parse == person));
        // true
        System.out.println("equals: " + parse.equals(person));

        System.out.println("id = " + parse.getId());
        System.out.println("name = " + parse.getName());
        System.out.println("age = " + parse.getAge());

    }

}
