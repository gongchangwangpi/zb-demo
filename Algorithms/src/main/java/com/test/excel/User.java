package com.test.excel;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Administrator
 * @date 2017/10/17
 */
@Getter
@Setter
public class User {
    Long id;
    String agentName;
    String password;
    String schoolId;
    long score;
    
    public User() {
    }

    public User(Long id, String agentName, String password, String schoolId, long score) {
        this.id = id;
        this.agentName = agentName;
        this.password = password;
        this.schoolId = schoolId;
        this.score = score;
    }
}
