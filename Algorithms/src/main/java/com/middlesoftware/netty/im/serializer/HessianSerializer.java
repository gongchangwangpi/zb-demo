package com.middlesoftware.netty.im.serializer;

import com.alibaba.com.caucho.hessian.io.Hessian2Input;
import com.alibaba.com.caucho.hessian.io.Hessian2Output;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

/**
 * @author zhangbo
 */
public class HessianSerializer implements Serializer {

    private static Logger logger = LoggerFactory.getLogger(HessianSerializer.class);
    
    @Override
    public byte SerializerType() {
        return SerializerType.HESSIAN;
    }

    @Override
    public byte[] serialize(Object obj) {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        // 2、对字节数组流进行再次封装

        // step 1. 定义外部序列化工厂
        //ExtSerializerFactory extSerializerFactory = new ExtSerializerFactory();
        //extSerializerFactory.addSerializer(java.time.OffsetDateTime.class, new OffsetDateTimeRedisSerializer());
        //extSerializerFactory.addDeserializer(java.time.OffsetDateTime.class, new OffsetDateTimeRedisDeserializer());
        // step 2. 序列化工厂
        //SerializerFactory serializerFactory = new SerializerFactory();
        //serializerFactory.addFactory(extSerializerFactory);

//        HessianOutput hessianOutput = new HessianOutput(bos);
//        //hessianOutput.setSerializerFactory(serializerFactory);
//
//        try {
//            // 注意，obj 必须实现Serializable接口
//            hessianOutput.writeObject(obj);
//            return bos.toByteArray();
//        } catch (IOException e) {
//            logger.error("出错啦", e);
//            throw new RuntimeException(e);
//        }
        
        Hessian2Output hessian2Output = new Hessian2Output(bos);
        try {
            // 注意，obj 必须实现Serializable接口
            hessian2Output.writeObject(obj);
            return bos.toByteArray();
        } catch (IOException e) {
            logger.error("出错啦", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<?> clz) {

        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);

//        HessianInput hessianInput = new HessianInput(bis);
//
//        try {
//            return (T) hessianInput.readObject(clz);
//        } catch (IOException e) {
//            logger.error("出错啦", e);
//            throw new RuntimeException(e);
//        }
        
        Hessian2Input hessian2Input = new Hessian2Input(bis);

        try {
            return (T) hessian2Input.readObject();
        } catch (IOException e) {
            logger.error("出错啦", e);
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        User u1 = new User(1L, "u1");
        User u2 = new User(2L, "u2");
        User u3 = new User(3L, "u3");

        Resp<User> resp = new Resp<>(200, u1, new User[]{u2, u3});

        HessianSerializer hessianSerializer = new HessianSerializer();

        byte[] bytes = hessianSerializer.serialize(resp);

        Resp<User> deserialize = hessianSerializer.deserialize(bytes, Resp.class);
        
        logger.info("resp class = {}", deserialize.getClass());
        logger.info("resp data class = {}", deserialize.getData().getClass());
        logger.info("resp data arr class = {}", deserialize.getDataArr().getClass());

    }
    
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    static class User implements Serializable {
        private static final long serialVersionUID = -3937283157784580234L;
        private Long id;
        private String name;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    static class Resp<T> implements Serializable {
        private static final long serialVersionUID = -250473541469343196L;
        private int code;
        private T data;
        private T[] dataArr;
    }
}
