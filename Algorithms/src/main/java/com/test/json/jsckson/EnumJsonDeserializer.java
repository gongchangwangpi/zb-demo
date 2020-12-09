package com.test.json.jsckson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

/**
 * jackson的枚举反序列化器
 *
 * @author zhangbo
 * @date 2020/11/14
 */
public class EnumJsonDeserializer extends JsonDeserializer<Enum<?>> {

    @Override
    public Enum<?> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        int intValue = p.getIntValue();
        ctxt.getContextualType();
        return null;
    }
}
