package com.zb.tcc.domain.user;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhangbo
 */
@Getter
@Setter
public class User implements Serializable {
    
    private static final long serialVersionUID = 8673032277800075330L;
    
    private Long id;
    
    private String username;
    
    private Integer age;
    
}
