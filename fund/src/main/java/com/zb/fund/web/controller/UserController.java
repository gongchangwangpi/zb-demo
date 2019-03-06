package com.zb.fund.web.controller;

import com.zb.fund.domain.User;
import com.zb.fund.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    
}
