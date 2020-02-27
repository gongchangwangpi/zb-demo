package com.zb.fund.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JobEnum implements Value {

    CODER(1),
    ENGINEER(2);

    @EnumValue
    private int value;
}
