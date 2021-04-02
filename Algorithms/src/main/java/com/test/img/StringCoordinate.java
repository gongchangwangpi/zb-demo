package com.test.img;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author bo6.zhang
 * @date 2021/3/12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StringCoordinate {

    /**
     * 字符串的字体大小
     */
    private Integer size;

    /**
     * 字符串在背景图中的坐标
     */
    private Integer x;

    /**
     * 字符串在背景图中的坐标
     */
    private Integer y;

}
