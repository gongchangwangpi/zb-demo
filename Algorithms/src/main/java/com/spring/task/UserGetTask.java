package com.spring.task;

import com.spring.domain.User;
import com.spring.service.UserService;

import java.util.concurrent.TimeUnit;

/**
 * Created by books on 2017/4/19.
 */
public class UserGetTask implements Runnable {

    private UserService userService;

    private Long id;

    public UserGetTask(UserService userService, Long id) {
        this.userService = userService;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        User user = userService.selectById(id);
        System.out.println("get user ----- " + user);
    }
}
