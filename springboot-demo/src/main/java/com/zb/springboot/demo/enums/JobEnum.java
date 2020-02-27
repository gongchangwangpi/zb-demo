package com.zb.springboot.demo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JobEnum {

    CODER(1),
    ENGINEER(2),
    LEADER(3),
    MANAGER(4);

//    @EnumValue
    private int value;
}
