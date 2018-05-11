package com.spring.service;

import com.spring.domain.User;
import com.spring.domain.query.UserQuery;

import java.util.List;

/**
 * Created by books on 2017/4/19.
 */
public interface UserService {

    List<User> selectListByQuery(UserQuery query);

    User selectById(Long id);

    Long save(User user);

    int updateAfter3s(User user);

    int delete(Long id);

    void updateAfter3s(Long id);

    void selectTwiceById(Long id);

    void update(Long id);

    void batchSave(List<User> users);

    void update1(User user);
    void update2(User user);
}
