package com.test.json;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

/**
 * json转对象时，下划线自动转驼峰的属性
 *
 * @author zhangbo
 * @date 2020/4/24
 */
public class GsonSnakeCase2CamelTest {

    public static void main(String[] args) throws IOException {

        String json = "{\"id\": 1,\"user_name\": \"name1\",\"class_id\": 2,\"sex\": \"F\"}";

//        Gson gson = new Gson();
        Gson gson = new GsonBuilder().setFieldNamingStrategy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        Student student = gson.fromJson(json, Student.class);

        System.out.println(student);
    }

}
