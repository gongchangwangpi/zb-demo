package com.zb.fund.job;

import com.zb.fund.domain.User;
import com.zb.fund.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhangbo
 */
//@Component
public class InsertUsersTestDataJob {
    
    @Autowired
    private UserService userService;
    
    @Scheduled(initialDelay = 1000, fixedRate = 1000 * 60 * 60 * 24)
    public void insert() {
        int threadCount = 16;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.execute(new InsertJob(userService));
        }
    }
    
    @Slf4j
    static class InsertJob implements Runnable {
        
        private UserService userService;

        public InsertJob(UserService userService) {
            this.userService = userService;
        }

        @Override
        public void run() {

            Random random = new Random();
            String[] sexArr = {"MALE", null, "FEMALE", null, null};
            String[] emailArr = {"@163.com", "@gmail.com", "@126.com", "@sina.com", "@qq.com", "@jhjhome.com"};

            int count = 1000;

            while (true) {

                List<User> users = new ArrayList<>(count);
                for (int i = 0; i < count; i++) {
                    String username = RandomStringUtils.randomAlphanumeric(12);
                    int age = random.nextInt(150);

                    User user = new User();
                    user.setUsername(username);
                    user.setAge(age);
                    user.setEmail(RandomStringUtils.randomAlphanumeric(8) + emailArr[age % 6]);
                    user.setSex(sexArr[age % 5]);
                    user.setCreateTime(new Date());
                    
                    users.add(user);
                }

                try {
                    userService.batchInsert(users);
                } catch (Exception e) {
                    log.error("【出错啦】", e);
                }
            }
            
        }
    }
}
