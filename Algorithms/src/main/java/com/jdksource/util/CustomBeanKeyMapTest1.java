package com.jdksource.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangbo
 */
public class CustomBeanKeyMapTest1 {

    public static void main(String[] args) throws Exception {

        Map<User, String> map = new HashMap<>();

        User user1 = new User(1L, "user1");
        map.put(user1, "1");

        // 1
        System.out.println(map.get(user1));
        System.out.println("hashCode = " + user1.hashCode());

        user1.setName("user2");
        System.out.println("hashCode = " + user1.hashCode());
        // null
        System.out.println(map.get(user1));

        user1.setName("user1");
        System.out.println("hashCode = " + user1.hashCode());
        // 1
        System.out.println(map.get(user1));
        
        User userKey = new User(1L, "user1");
        System.out.println("hashCode = " + userKey.hashCode());
        // 1
        System.out.println(map.get(userKey));
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    static class User {
        private Long id;
        private String name;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            User user = (User) o;

            if (!id.equals(user.id)) return false;
            return name.equals(user.name);
        }

        @Override
        public int hashCode() {
            int result = id.hashCode();
            result = 31 * result + name.hashCode();
            return result;
        }
    }
    
}
