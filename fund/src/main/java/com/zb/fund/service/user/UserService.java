package com.zb.fund.service.user;

import com.zb.fund.domain.User;

import java.util.List;

/**
 * @author zhangbo
 */
public interface UserService {
    
    int batchUpdate(List<User> users);
    
}
