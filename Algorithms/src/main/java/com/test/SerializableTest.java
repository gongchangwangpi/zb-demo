package com.test;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 修改serialVersionUID后，使用jdk自带的序列化
 * 
 * @author zhangbo
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SerializableTest implements Serializable {
    
//    private static final long serialVersionUID = -8993637369194193462L;
    private static final long serialVersionUID = -8993637369194193432L;
    
    private Long id;
    
    private String name;

    public static void main(String[] args) throws Exception {

        String filePath = "F:\\obj.out";

//        writeObj(filePath);
        
        readObj(filePath);
        
    }
    
    private static void writeObj(String filePath) throws Exception {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(new SerializableTest(2L, "张三"));
        }
    }
    
    private static void readObj(String filePath) throws Exception {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            Object o = ois.readObject();
            System.out.println(JSON.toJSONString(o));
        }
    }
}
