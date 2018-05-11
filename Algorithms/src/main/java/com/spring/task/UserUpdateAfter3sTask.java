package com.spring.task;

import com.spring.service.UserService;

/**
 * Created by books on 2017/4/19.
 */
public class UserUpdateAfter3sTask implements Runnable {

    private UserService userService;

    private Long id;

    public UserUpdateAfter3sTask(UserService userService, Long id) {
        this.userService = userService;
        this.id = id;
    }

    @Override
    public void run() {
        userService.updateAfter3s(id);
    }
}
