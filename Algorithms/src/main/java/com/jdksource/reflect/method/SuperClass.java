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
public class SuperClass {
    
    private Long id;
    
    private String name;
    
    private int age;
    
    protected int protectedProp;
    
    public int publicProp;
    
    public boolean isAdult() {
        return age >= 18;
    }
    
    private void printName() {
        System.out.println(name);
    }
}
