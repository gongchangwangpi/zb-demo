package com.jdksource.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangbo
 */
public class HashMapTest3Treeify {

    public static void main(String[] args) {
        
        Map<User, String> userMap = new HashMap<>();

        for (int i = 0; i < 5000000; i++) {
            userMap.put(new User(i), String.valueOf(i));
        }
        
    }

    
 
    static class User {
        private Integer id;

        public User() {
            System.out.println("---- user");
        }

        public User(Integer id) {
            this.id = id;
//            System.out.println("---- user " + id);
        }
    }
}
