package com.zb.fund.web.controller;

import com.zb.fund.domain.ManUser;
import com.zb.fund.domain.User;
import com.zb.fund.service.user.IManUserService;
import com.zb.fund.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zhangbo
 */
@RequestMapping(value = "/user")
@RestController
public class UserController {
    
    @Autowired
    private IManUserService iManUserService;

    @Autowired
    private UserService userService;
    
    @RequestMapping(value = "/batchUpdate")
    public Object batchUpdate() {

        List<User> users = new ArrayList<>();
        User user1 = new User(1L, "zhangsan", 10, "M", null, new Date());
        users.add(user1);
        User user2 = new User(2L, "lisi", 20, "M", null, new Date());
        users.add(user2);
        User user3 = new User(3L, "wangwu", 30, "M", null, new Date());
        users.add(user3);
        
        return userService.batchUpdate(users);
    }

    @GetMapping(value = "/list")
    public ManUser list(Long id) {
        return iManUserService.get(id);
    }

    @GetMapping(value = "/{id}")
    public ManUser get(@PathVariable("id") Long id) {
        return iManUserService.get(id);
    }

    @GetMapping(value = "/407")
    public ResponseEntity auth407(Long id, HttpServletResponse response) throws IOException {
        return new ResponseEntity(HttpStatus.PROXY_AUTHENTICATION_REQUIRED);
    }

    @GetMapping(value = "/401")
    public ResponseEntity auth401(Long id, HttpServletResponse response) throws IOException {
        return new ResponseEntity<>("bad 401", HttpStatus.BAD_REQUEST);
    }
}
