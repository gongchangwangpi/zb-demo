package com.test.json.fastjson;

import com.alibaba.fastjson.JSON;
import com.test.json.Student;

import java.io.IOException;

/**
 * json转对象时，下划线自动转驼峰的属性
 *
 * @author zhangbo
 * @date 2020/4/24
 */
public class FastjsonSnakeCase2CamelTest {

    public static void main(String[] args) throws IOException {

        String json = "{\"id\": 1,\"user_name\": \"name1\",\"class_id\": 2,\"sex\": \"F\"}";

        Student student = JSON.parseObject(json, Student.class);

        System.out.println(student);
    }

}
