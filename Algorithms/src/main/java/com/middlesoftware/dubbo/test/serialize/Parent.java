package com.middlesoftware.dubbo.test.serialize;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author zhangbo
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Parent implements Serializable {

    private static final long serialVersionUID = -5992792086252830238L;
    
    private Long id;
    
    private String name;
    
    private String code;
    
}
