package com.jdksource.reflect;

import com.alibaba.fastjson.TypeReference;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by books on 2017/12/18.
 */
public class Test {

    public static void main(String[] args) {

//        Map<String, Integer> map = new HashMap<>();
//
//        Class<? extends Map> clz = map.getClass();

        TypeReference<Map<String, Integer>> typeReference = new TypeReference<Map<String, Integer>>() {
        };

        Class<? extends TypeReference> clz = typeReference.getClass();
        System.out.println(clz);

        Type genericSuperclass = clz.getGenericSuperclass();
        System.out.println(genericSuperclass);

        Class<?> superclass = clz.getSuperclass();
        System.out.println(superclass);

        Type[] actualTypeArguments = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
        System.out.println(Arrays.toString(actualTypeArguments));

        
    }
    
}
