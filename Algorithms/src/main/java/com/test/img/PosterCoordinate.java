package com.test.img;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

/**
 * @author bo6.zhang
 * @date 2021/3/15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PosterCoordinate {

    @NotNull(message = "二维码坐标信息不能为空")
    private ImageCoordinate qrcodeCoordinate;

    @NotNull(message = "头像坐标信息不能为空")
    private ImageCoordinate avatarCoordinate;

    @NotNull(message = "昵称坐标信息不能为空")
    private StringCoordinate nickNameCoordinate;

}
