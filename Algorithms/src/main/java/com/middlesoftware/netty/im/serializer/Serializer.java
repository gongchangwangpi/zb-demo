package com.middlesoftware.netty.im.serializer;

/**
 * @author zhangbo
 */
public interface Serializer {
    
    /**
     * 默认使用JSON
     */
    Serializer DEFAULT = new JSONSerializer();
    
    byte SerializerType();
    
    byte[] serialize(Object o);
    
    <T> T deserialize(byte[] bytes, Class<?> clz);
}
