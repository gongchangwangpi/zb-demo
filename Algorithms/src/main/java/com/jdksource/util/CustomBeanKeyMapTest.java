package com.jdksource.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangbo
 */
public class CustomBeanKeyMapTest {

    public static void main(String[] args) throws Exception {

        Map<User, String> map = new HashMap<>();

        User user1 = new User(1L, "user1");
        map.put(user1, "1");

        System.out.println(map.get(user1));
        System.out.println("hashCode = " + user1.hashCode());

        user1.setName("user2");
        System.out.println("hashCode = " + user1.hashCode());
        System.out.println(map.get(user1));

        user1.setName("user1");
        System.out.println("hashCode = " + user1.hashCode());
        System.out.println(map.get(user1));
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class User {
        private Long id;
        private String name;
    }
    
}
