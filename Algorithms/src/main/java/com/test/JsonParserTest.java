package com.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.spring.domain.User;

/**
 * @author zhangbo
 * @date 2020/5/9
 */
public class JsonParserTest {

    public static <T> T parse(String json, Class<T> type) {
        return JSON.parseObject(json, type);
    }

    public static <T> T parse(String json, TypeReference<T> type) {
        return JSON.parseObject(json, type);
    }

    public static void main(String[] args) {
        Object parse = parser.parse("{\"id\":1}", User.class);
        System.out.println(parse);
    }

    public static Parser parser = JSON::parseObject;
    public static Parser1 parser1 = JSON::parseObject;

    interface Parser<T> {
        T parse(String json, Class<T> type);
    }
    interface Parser1<T> {
        T parse(String json, TypeReference<T> type);
    }

}
