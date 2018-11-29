package com.jdksource.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangbo
 */
public class HashMapTest3Resize {

    public static void main(String[] args) {
        
        System.setProperty("jdk.internal.lambda.dumpProxyClasses", "E://");
        
        put4Resize();

        computeIfAbsent4Resize();
    }

    private static void computeIfAbsent4Resize() {
        long start = System.nanoTime();
        Map<String, User> map = new HashMap<>(4);

        for (int i = 0; i < 6; i++) {
            final int id = i;
            map.computeIfAbsent("" + i, s -> new User(id));
        }
        long end = System.nanoTime();
        System.out.println(end - start);
    }

    private static void put4Resize() {
        long start = System.nanoTime();
        Map<String, User> map = new HashMap<>(4);

        for (int i = 0; i < 6; i++) {
            User user = map.get("" + i);
            if (user == null) {
                map.put(i + "", new User(i));
            }
        }
        long end = System.nanoTime();
        System.out.println(end - start);
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
