package com.zookeeper.jute;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.alibaba.fastjson.JSON;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.jute.*;

/**
 * Zookeeper默认序列化组件 jute
 * 共有三种序列化方式 
 * {@link BinaryOutputArchive}
 * {@link CsvOutputArchive}
 * {@link XmlOutputArchive}
 * 
 * @author zhangbo
 */
public class JuteTest {

    public static void main(String[] args) throws Exception {
        
        // 序列化到文件中
        FileOutputStream fos = new FileOutputStream("E://jute");
        BinaryOutputArchive outputArchive = BinaryOutputArchive.getArchive(fos);

        Req req = new Req(1L, "req", 20);
        
        req.serialize(outputArchive, "");

        // 反序列化
        FileInputStream fis = new FileInputStream("E://jute");
        BinaryInputArchive inputArchive = BinaryInputArchive.getArchive(fis);

        Req res = new Req();
        res.deserialize(inputArchive, "");

        System.out.println(JSON.toJSONString(res));
    }
    
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    static class Req implements Record {

        private Long id;
        private String username;
        private int age;
        
        @Override
        public void serialize(OutputArchive archive, String tag) throws IOException {
            archive.startRecord(this, tag);
            archive.writeLong(this.id, "id");
            archive.writeString(this.username, "username");
            archive.writeInt(this.age, "age");
            archive.endRecord(this, tag);
        }

        @Override
        public void deserialize(InputArchive archive, String tag) throws IOException {
            archive.startRecord(tag);
            this.id = archive.readLong("id");
            this.username = archive.readString("username");
            this.age = archive.readInt("age");
            archive.endRecord(tag);
        }
    }
}
