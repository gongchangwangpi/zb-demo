package com.zb.springboot.demo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GenderEnum {

    M(1), // 男
    F(2), // 女
    N(3); // 未知

//    @EnumValue
    private int value;
}
