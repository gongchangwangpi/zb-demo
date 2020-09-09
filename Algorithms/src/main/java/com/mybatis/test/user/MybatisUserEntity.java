package com.mybatis.test.user;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author zhangbo
 * @date 2020/9/7
 */
@Data
@TableName(value = "t_user")
public class MybatisUserEntity {

    private Integer id;

    private String username;

    private Integer age;

    private Date createTime;
    private Date registerTime;

    private String sex;
}
