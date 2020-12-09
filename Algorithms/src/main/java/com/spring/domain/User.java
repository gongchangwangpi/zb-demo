package com.spring.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.spring.domain.enums.SexEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by books on 2017/4/19.
 */
@Getter
@Setter
@TableName("t_user")
public class User {

    @TableField(value = "")
    private Long id;

    private String username;

    private Integer age;
    
    private SexEnum sex;

    public User() {
    }

    public User(String username) {
        this.username = username;
    }

    public User(String username, Integer age) {
        this.username = username;
        this.age = age;
    }

    /*@Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", agentName='" + username + '\'' +
                '}';
    }*/
}
