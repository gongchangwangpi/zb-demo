package com.spring.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhangbo
 */
@Getter
@AllArgsConstructor
public enum  SexEnum {
    
    MALE("男"),
    FEMALE("女"),
    UNKNOWN("未知");
    
    private String desc;
    
}
