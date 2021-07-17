package com.test.json.jsckson;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.test.json.DateConstant;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author bo6.zhang
 * @date 2021/7/15
 */
public class JsonSerializerTest {

    static ObjectMapper MAPPER = new ObjectMapper();

    public static void main(String[] args) throws Exception {

        Person person = new Person();

        String json = MAPPER.writeValueAsString(person);

        System.out.println(json);

    }

    static {
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern("HH:mm:ss")));

        MAPPER.registerModules(new ParameterNamesModule(), new Jdk8Module(), javaTimeModule)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    @Data
    public static class Person {

        private String name = "zhangsan";

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        private LocalDateTime createTime = LocalDateTime.now();

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        @JsonSerialize(using = LocalDateTimeJsonSerializer.class)
        private LocalDateTime newTime = LocalDateTime.now();

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        @JsonSerialize(using = LocalDateTimeJsonSerializer.class)
        private LocalDateTime updateTime = DateConstant.DB_DEFAULT_TIME;

        private LocalDateTime modifyTime = LocalDateTime.now();

    }

}
