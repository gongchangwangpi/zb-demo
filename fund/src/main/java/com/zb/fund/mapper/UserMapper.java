package com.zb.fund.mapper;

import com.zb.fund.domain.User;

import java.util.List;

public interface UserMapper {
    
    int deleteByPrimaryKey(Long id);

    int insert(User record);
    
    int batchInsert(List<User> list);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    int batchUpdate(List<User> list);
}