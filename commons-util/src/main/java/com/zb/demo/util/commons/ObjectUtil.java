package com.zb.demo.util.commons;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

/**
 * 常用工具类
 *
 * @author zhangbo
 * @date 2019-07-29
 */
public class ObjectUtil {

    public static <T> T defaultValue(T value, T defaultValue) {
        Objects.requireNonNull(defaultValue, "defaultValue is null");
        return value != null ? value : defaultValue;
    }

    public static <T, R> R defaultIfNull(T t, Function<T, R> function, R defaultValue) {
        return Optional.ofNullable(t).map(function).orElse(defaultValue);
    }

}
