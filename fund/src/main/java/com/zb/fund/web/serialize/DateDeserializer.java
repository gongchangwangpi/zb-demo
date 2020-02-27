package com.zb.fund.web.serialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.Date;

/**
 * @author zhangbo
 * @date 2019-08-15
 */
public class DateDeserializer extends JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        return new Date(p.getLongValue());
    }
}
