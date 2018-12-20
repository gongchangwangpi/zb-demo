package com.spring.domain.query;

import com.spring.domain.enums.SexEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by books on 2017/4/19.
 */
@Getter
@Setter
public class UserQuery {

    private Long id;

    private String username;
    
    private SexEnum sex;

}
