package com.zb.fund.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
    
    private Long id;

    private String username;

    private Integer age;

    private String sex;

    private String email;

    private Date createTime;

}