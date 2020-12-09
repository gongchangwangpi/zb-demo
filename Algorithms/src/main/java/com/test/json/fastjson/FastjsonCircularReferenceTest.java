package com.test.json.fastjson;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * fastjson 循环引用
 * 
 * @author zhangbo
 */
public class FastjsonCircularReferenceTest {

    public static void main(String[] args) {

        Map<String, Object> map1 = new HashMap<>();
        Map<String, Object> map2 = new HashMap<>();
        
        map1.put("1", map2);
        map2.put("2", map1);

        // 关闭循环引用检测，会导致 java.lang.StackOverflowError
//        String json = JSON.toJSONString(map1, SerializerFeature.DisableCircularReferenceDetect);
        String json = JSON.toJSONString(map1);
        
//        “$ref”:”..” 上一级
//        “$ref”:”@” 当前对象，也就是自引用
//        “$ref”:”$” 根对象
//        “$ref”:”$.children.0” 基于路径的引用，相当于root.getChildren().get(0)
        System.out.println(json);
    }
    
}
