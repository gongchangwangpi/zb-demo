package com.netty.qwzn.chapter8;

import com.google.protobuf.ByteString;

/**
 * @author zhangbo
 */
public class PersonTest {

    public static void main(String[] args) throws Exception {
        
        String path = "E:\\openproj\\github-fork\\zb-demo\\Algorithms\\src\\main\\java\\com\\netty\\qwzn\\chapter8\\personproto.proto";

//        PersonOuterClass.Person person = PersonOuterClass.Person.newBuilder().setId(11).setName("person test").build();
        PersonOuterClass.Person person = PersonOuterClass.Person.newBuilder().setId(11).setName("person test").build();
        
//        person.writeDelimitedTo(new FileOutputStream(path));
//        PersonOuterClass.Person parseFrom = PersonOuterClass.Person.parseFrom(new FileInputStream(path));

//        byte[] bytes = person.toByteArray();
//        PersonOuterClass.Person parseFrom = PersonOuterClass.Person.parseFrom(bytes);

        ByteString byteString = person.toByteString();
        PersonOuterClass.Person parseFrom = PersonOuterClass.Person.parseFrom(byteString);
        
        System.out.println(parseFrom.getId());
        System.out.println(parseFrom.getName());

    }
    
}
