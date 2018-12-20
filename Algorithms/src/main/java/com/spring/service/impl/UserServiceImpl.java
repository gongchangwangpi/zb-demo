package com.spring.service.impl;

import com.spring.dao.UserMapper;
import com.spring.domain.User;
import com.spring.domain.query.UserQuery;
import com.spring.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by books on 2017/4/19.
 */
@Service(value = "userService")
@Transactional(propagation = Propagation.REQUIRED)
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<User> selectListByQuery(UserQuery query) {
        return userMapper.selectListByQuery(query);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public User selectById(Long id) {
        User user = userMapper.selectById(id);

        return user;
    }

    @Override
    public Long save(User user) {
        System.out.println("save ----- start");
        userMapper.insert(user);
        /*try {
            // 模拟耗时,方便后面的查询线程查询到未提交的数据
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        System.out.println("save ----- end");
        return user.getId();
    }

    @Override
    public int updateAfter3s(User user) {
        System.out.println("update ----- start");

        int update = userMapper.update(user);
        try {
            // 模拟耗时,方便后面的查询线程查询到未提交的数据
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("update ----- end");
        return update;
    }

    @Override
    public int delete(Long id) {
        return userMapper.delete(id);
    }

    @Override
    public void updateAfter3s(Long id) {
        System.out.println("update ----- start");

        User user = userMapper.selectById(id);
        System.out.println("before update ----- " + user);

        user.setUsername("未提交");
        try {
            // 模拟耗时,方便后面的查询线程查询到未提交的数据
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        userMapper.update(user);
        System.out.println("after update ----- " + user);

        System.out.println("update ----- end");
    }

    @Override
    public void selectTwiceById(Long id) {
        User user = userMapper.selectById(id);
        System.out.println("get once ----- " + user);

        try {
            // 模拟耗时
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        User user2 = userMapper.selectById(id);
        System.out.println("get twice ----- " + user2);
    }

    @Override
    public void update(Long id) {
        System.out.println("update ----- start");

        User user = userMapper.selectById(id);
        System.out.println("before update ----- " + user);

        user.setUsername("未提交");

        userMapper.update(user);
        System.out.println("after update ----- " + user);

        System.out.println("update ----- end");
    }

    @Override
    public void batchSave(List<User> users) {
        userMapper.batchInsert(users);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void update1(User user) {
        userMapper.update(user);
        user.setId(3L);
        update2(user);
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int updateUser(User user) {
        return userMapper.update(user);
    }

    @Override
    @Transactional(propagation = Propagation.NESTED)
    public void update2(User user) {
        userMapper.update(user);
        int i = 1 / 0;
    }


}
