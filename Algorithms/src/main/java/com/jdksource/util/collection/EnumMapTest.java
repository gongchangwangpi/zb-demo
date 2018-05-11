package com.jdksource.util.collection;

import java.util.EnumMap;

/**
 * Created by books on 2017/11/3.
 */
public class EnumMapTest {

    public static void main(String[] args) {


        EnumMap<UserTypeEnum, String> enumMap = new EnumMap<>(UserTypeEnum.class);
        
        enumMap.put(UserTypeEnum.consumer, "consumer1");
        enumMap.put(UserTypeEnum.agent, "agent1");

        System.out.println(enumMap);
        
    }
    
    enum UserTypeEnum {
        consumer,
        agent
    }
    
}
