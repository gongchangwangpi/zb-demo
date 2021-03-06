package com.zb.springboot.demo.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zb.springboot.demo.entity.User;

/**
 * @author zhangbo
 * @date 2020/4/23
 */
public interface UserService extends IService<User> {

    Long insert(User user);

    /**
     * 测试事务隔离级别
     * 前后查询两次，分别使用默认的隔离级别和read-committed
     * 代码中sleep5秒，然后手动修改数据库中的值
     *
     * @return
     */
    User txIsolationTest();


    Long insertPropagation() throws Exception;


    Long insertPropagationNew();

    User get(Long id);
    User get(String username);

}
