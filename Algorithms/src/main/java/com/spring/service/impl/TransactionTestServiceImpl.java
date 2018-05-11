//package com.spring.service.impl;
//
//import com.spring.domain.User;
//import com.spring.domain.query.UserQuery;
//import com.spring.service.TransactionTestService;
//import com.spring.service.UserService;
//import com.spring.task.UserDoubleGetTask;
//import com.spring.task.UserGetTask;
//import com.spring.task.UserUpdateAfter3sTask;
//import com.spring.task.UserUpdateTask;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.annotation.Resource;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
///**
// * Created by books on 2017/4/19.
// */
//@Service(value = "transactionTestService")
//@Transactional(propagation = Propagation.SUPPORTS)
//public class TransactionTestServiceImpl implements TransactionTestService {
//
//    @Resource
//    private UserService userService;
//
//    @Override
//    public void readUncommitted() {
//        // 当一个事务正在访问数据，并且对数据进行了修改，而这种修改还没有提交到数据库中，
//        // 这时，另外一个事务也访问这个数据，然后使用了这个数据。
//
//        Thread thread1 = new Thread(new UserUpdateAfter3sTask(userService, 1L));
//        thread1.start();
//
//        Thread thread2 = new Thread(new UserGetTask(userService, 1L));
//        thread2.start();
//
//    }
//
//    @Override
//    public void readCommitted() {
//        Thread thread2 = new Thread(new UserDoubleGetTask(userService, 1L));
//        thread2.start();
//
//        try {
//            TimeUnit.MILLISECONDS.sleep(500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        Thread thread1 = new Thread(new UserUpdateTask(userService, 1L));
//        thread1.start();
//    }
//
//    @Override
//    public void repeatableRead() {
//        UserQuery query = new UserQuery();
//        List<User> users = userService.selectListByQuery(query);
//        System.out.println(users.size());
//
//        User user = new User();
//        user.setAgentName("新增");
//        userService.save(user);
//
//        List<User> userList = userService.selectListByQuery(query);
//        System.out.println(userList.size());
//    }
//
//    @Override
//    public void serializable() {
//
//    }
//}
