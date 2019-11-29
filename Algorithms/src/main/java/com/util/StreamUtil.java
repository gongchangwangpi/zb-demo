package com.util;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;


/**
 * @author zhangbo
 * @date 2019-11-29
 */
public class StreamUtil {

    public static <R, T, V> Map<R, V> list2map(List<T> list, Function<T, R> keyMapper, Function<T, V> valueMapper) {
        return list.stream().collect(toMap(keyMapper, valueMapper));
    }

    public static <R, T> Map<R, T> list2map(List<T> list, Function<T, R> keyMapper) {
        return list2map(list, keyMapper, (v) -> {
            return v;
        });
    }

}
