package com.spring.service.impl;

import com.alibaba.fastjson.JSON;
import com.spring.dao.UserMapper;
import com.spring.domain.User;
import com.spring.domain.query.UserQuery;
import com.spring.service.ReadCommitTestService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by books on 2017/9/22.
 */
@Service(value = "readCommitTestService")
@Transactional
public class ReadCommitTestServiceImpl implements ReadCommitTestService {
    
    @Resource
    private UserMapper userMapper;

    /**
     * 在同一个事务中，后面的读操作会读取到本事务修改的东西
     * 
     */
    @Override
    public void readCommit() {
        User user = userMapper.selectById(1L);
        System.out.println(JSON.toJSONString(user));

        user.setUsername("readCommit");
        userMapper.update(user);

        user = userMapper.selectById(1L);
        System.out.println(JSON.toJSONString(user));
    }

    /**
     * 在同一个事务中两次读取，如果在第一次读取后中间有其他的事务修改了数据并已提交
     * 则后面的一次读取和第一次读取的数据相同，不会读取到其他事务修改的数据
     */
    @Override
    public void readRepeatable() {
        UserQuery query = new UserQuery();
        List<User> users = userMapper.selectListByQuery(query);
        System.out.println(JSON.toJSONString(users));
        
        // 这里需要另外的事务修改数据，可以暂停（断点）后手动修改数据库数据，
        // 然后在让后面的代码重新读取
        
        users = userMapper.selectListByQuery(query);
        System.out.println(JSON.toJSONString(users));
    }

    /**
     * 
     */
    @Override
    public void phantomRead() {
        UserQuery query = new UserQuery();
        List<User> users = userMapper.selectListByQuery(query);
        System.out.println(JSON.toJSONString(users));

        User user = new User();
        user.setAge(55);
        // 修改数据库所有的行
        userMapper.update(user);

        // 这里需要另外的事务修改数据，可以暂停（断点）后手动增加一条数据或者修改了相同数据的相同列，
        // 然后这个事务会发现数据库有的数据没有修改到，就像发生幻觉一样，产生幻读

        users = userMapper.selectListByQuery(query);
        System.out.println(JSON.toJSONString(users));
    }
}
