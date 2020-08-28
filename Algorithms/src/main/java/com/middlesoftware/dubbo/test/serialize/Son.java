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
public class Son extends Parent implements Serializable {

    private static final long serialVersionUID = 8043945169667723781L;
    
    private String code;

}
