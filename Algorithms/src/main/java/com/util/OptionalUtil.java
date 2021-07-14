package com.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Function;

/**
 * @author bo6.zhang
 * @date 2021/3/2
 */
public class OptionalUtil {

    public static void main(String[] args) {
        System.out.println(get(State.OPEN, State::getDesc));
        System.out.println(get(null, State::getDesc));

    }

    private static <T, R> R get(T t, Function<T, R> function) {
        if (t == null) {
            return null;
        }
        return function.apply(t);
    }

    @Getter
    @AllArgsConstructor
    enum State {
        OPEN("开启"),
        CLOSE("关闭");
        private String desc;
    }
}
