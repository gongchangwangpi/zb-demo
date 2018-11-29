package com.jdksource.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangbo
 */
public class HashMapTest2 {

    public static void main(String[] args) {
        
        Map<String, User> map = new HashMap<>();

        for (int i = 0; i < 5; i++) {
            final int id = i;
            User user = map.computeIfAbsent("123", s -> {
                return new User(id);
            });

            System.out.println(user);
            System.out.println(map);
        }
        
    }
 
    static class User {
        private Integer id;

        public User() {
            System.out.println("---- user");
        }

        public User(Integer id) {
            this.id = id;
            System.out.println("---- user " + id);
        }
    }
}
