package com.spring.controller;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangbo
 */
@RestController
@RequestMapping(value = "/jsonview")
public class JacksonJsonViewController {
    
    @RequestMapping(value = "/detail")
    @JsonView(value = User.UserDetail.class)
    public User jsonview1() {
        User user = new User(1L, "张三", 17);
        return user;
    }
    
    @RequestMapping(value = "/name")
    @JsonView(value = User.UserName.class)
    public User jsonview2() {
        User user = new User(1L, "张三", 17);
        return user;
    }
    
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class User {
        public interface UserName {}
        public interface UserDetail extends UserName {}
        
        @JsonView(value = UserDetail.class)
        private Long id;
        @JsonView(value = UserName.class)
        private String name;
        @JsonView(value = UserDetail.class)
        private int age;
    }
}
