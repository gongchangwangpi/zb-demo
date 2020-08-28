package com.middlesoftware.netty.qwzn.chapter7;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author zhangbo
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User implements Serializable {
    
    private static final long serialVersionUID = 3639354173732386021L;
    
    private Integer id;
    
    private String name;
    
}
