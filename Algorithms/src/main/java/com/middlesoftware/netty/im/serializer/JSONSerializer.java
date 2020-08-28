package com.middlesoftware.netty.im.serializer;

import com.alibaba.fastjson.JSON;

/**
 * JSON序列化，默认使用fastjson
 * 
 * @author zhangbo
 */
public class JSONSerializer implements Serializer {
    
    
    @Override
    public byte SerializerType() {
        return SerializerType.JSON;
    }

    @Override
    public byte[] serialize(Object o) {
        return JSON.toJSONBytes(o);
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<?> clz) {
        return JSON.parseObject(bytes, clz);
    }
}
