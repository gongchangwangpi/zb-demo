package com.test.json.jsckson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.test.json.Student;

import java.io.IOException;

/**
 * json转对象时，下划线自动转驼峰的属性
 *
 * @author zhangbo
 * @date 2020/4/24
 */
public class JacksonSnakeCase2CamelTest {

    public static void main(String[] args) throws IOException {
        String json = "{\"id\": 1,\"user_name\": \"name1\",\"class_id\": 2,\"sex\": \"F\"}";

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

        Student student = objectMapper.readValue(json, Student.class);

        System.out.println(student);
    }

}
