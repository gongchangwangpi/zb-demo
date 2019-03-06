package com.zb.fund.web.controller;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * BigDecimal格式化为两位小数
 * 
 * @author zhangbo
 */
public class BigDecimalSerializer extends JsonSerializer<BigDecimal> {
    
    private static final DecimalFormat FORMAT = new DecimalFormat("0.00");
    
    @Override
    public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        gen.writeNumber(FORMAT.format(value));
    }
}
