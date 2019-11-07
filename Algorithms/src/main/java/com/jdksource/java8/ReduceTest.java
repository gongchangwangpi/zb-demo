package com.jdksource.java8;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author zhangbo
 * @date 2019-11-07
 */
public class ReduceTest {

    public static void main(String[] args) {

        Integer integer = Stream.of(1, 2, 3, 4, 5).reduce(0, (i, j) -> {
            return i + j;
        });
        System.out.println(integer);

        Integer i1 = Stream.of(1, 2, 3, 4, 5).reduce(1, (i, j) -> {
            return i * j;
        });
        System.out.println(i1);

        Optional<Integer> optional = Stream.of(1, 2, 3, 4, 5).reduce((i, j) -> {
            return i * j;
        });
        System.out.println(optional.orElse(-1));

        Optional<Integer> optional2 = Stream.of(1, 2, 3, null, 5).reduce((i, j) -> {
            return i * j;
        });
        System.out.println(optional2.orElse(-1));
    }

}
