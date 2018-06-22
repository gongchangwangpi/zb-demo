package com.jdksource.lambda;

import java.util.Optional;

import com.alibaba.fastjson.JSON;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangbo
 */
@Slf4j
public class OptionalTest {

    public static void main(String[] args) {

        User u = null;

//        Optional<User> optional = Optional.of(u);
        Optional<User> optional = Optional.ofNullable(u);
        User user = optional.orElse(createUser(1L));
        log.info("orElse: {}", JSON.toJSONString(user));
        
        log.info("optional: {}", optional.get());

    }

    private static User createUser(Long id) {
        User u = new User(id, "u1", 1);
        log.info("createUser: -->>> {}", JSON.toJSONString(u));
        return u;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    static class User {
        private Long id;
        private String username;
        private int age;
    }
}
