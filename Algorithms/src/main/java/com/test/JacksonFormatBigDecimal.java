package com.test;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author zhangbo
 */
@Slf4j
public class JacksonFormatBigDecimal {

    public static void main(String[] args) throws JsonProcessingException {

        User lisi = new User("lisi", new BigDecimal("20.5689"));
        lisi.setAmount(null);
        ObjectMapper objectMapper = new ObjectMapper();

        String s = objectMapper.writeValueAsString(lisi);

        System.out.println(s);
    }


    @NoArgsConstructor
    @AllArgsConstructor
    static class User {
        private String name;
        @JsonSerialize(using = BigDecimalSerializer.class)
        private BigDecimal amount;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }
    }
    
    static class BigDecimalSerializer extends JsonSerializer<BigDecimal> {
        static final DecimalFormat FORMAT = new DecimalFormat("0.00");
        @Override
        public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
            gen.writeNumber(FORMAT.format(value));
        }
    }
    
}
