package com.jdksource.reflect.method;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author zhangbo
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubClass extends SuperClass {
    
    private Long id;
    
    private String subName;
    
    private String appendSuperName() {
        return subName + getName();
    }
    
}
