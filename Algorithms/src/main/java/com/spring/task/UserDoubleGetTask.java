package com.spring.task;

import com.spring.service.UserService;

/**
 *
 *
 * Created by books on 2017/4/19.
 */
public class UserDoubleGetTask implements Runnable {

    private UserService userService;

    private Long id;

    public UserDoubleGetTask(UserService userService, Long id) {
        this.userService = userService;
        this.id = id;
    }

    @Override
    public void run() {
        userService.selectTwiceById(id);
    }
}
