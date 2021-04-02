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
public class ImageCoordinate {

    /**
     * 图片在背景图中的坐标
     */
    private Integer x;

    /**
     * 图片在背景图中的坐标
     */
    private Integer y;

    /**
     * 图片在背景图中的长度
     */
    private Integer width;

    /**
     * 图片在背景图中的高度
     */
    private Integer height;

}
