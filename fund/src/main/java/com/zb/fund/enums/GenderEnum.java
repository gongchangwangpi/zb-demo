package com.zb.fund.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GenderEnum implements Value {

    M(1),
    F(2);

    @EnumValue
    private int value;
}
