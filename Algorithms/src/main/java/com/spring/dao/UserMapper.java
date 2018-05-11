package com.spring.dao;

import com.spring.domain.User;
import com.spring.domain.query.UserQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by books on 2017/4/19.
 */
@Repository
public interface UserMapper {

    List<User> selectListByQuery(UserQuery query);

    User selectById(Long id);

    int insert(User user);

    int batchInsert(List<User> users);

    int update(User user);

    int delete(Long id);
}
