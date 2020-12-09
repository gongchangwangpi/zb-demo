package com.test.json;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhangbo
 * @date 2020/11/14
 */
@Getter
@AllArgsConstructor
public enum StudentStatusEnum {

    Y(1),
    N(0);

    private int status;

}
