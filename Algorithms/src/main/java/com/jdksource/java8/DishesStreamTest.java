package com.jdksource.java8;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * @author zhangbo
 */
public class DishesStreamTest {

    public static void main(String[] args) {

        List<Dishes> dishesList = getDishes();

        // 只是执行stream相关的操作,并不会触发真实的操作
//        dishesList.stream()
//                .filter(dishes -> dishes.getColor().equals("green"))
//                .map(Dishes::getName);

        Stream<Dishes> stream1 = dishesList.stream();
        Stream<Dishes> stream2 = stream1.filter(dishes -> dishes.getColor().equals("green"));
        Stream<String> stream3 = stream2.map(Dishes::getName);
        List<String> list = stream3.collect(toList());


        // 从打印结果可以看出，stream是流式处理的，
        // 并不是等待前面一个操作执行完毕之后在执行后面一个操作，
        // 在获取到green的小白菜之后立即执行了getName方法
//        List<String> green = dishesList.stream()
//                .filter(dishes -> dishes.getColor().equals("green"))
//                .map(Dishes::getName)
//                .collect(toList());

        // 打印具体的操作步骤
        List<String> green = dishesList.stream()
                .filter(dishes -> {
                    System.out.println("filter : " + dishes.getColor());
                    return dishes.getColor().equals("green");
                })
                .map(dishes -> {
                    System.out.println("map : " + dishes.getName());
                    return dishes.getName();
                })
                .collect(toList());

        green.forEach(System.out::println);
        
    }
    
    private static List<Dishes> getDishes() {
        List<Dishes> dishesList = new ArrayList<>();
        
        dishesList.add(new Dishes("土豆丝", 100, "yellow", 12.5));
        dishesList.add(new Dishes("小白菜", 60, "green", 10.0));
        dishesList.add(new Dishes("回锅肉", 500, "white", 48.8));
        dishesList.add(new Dishes("青椒肉丝", 450, "green", 35.5));
        dishesList.add(new Dishes("水煮牛肉", 600, "red", 59.5));
        dishesList.add(new Dishes("火锅", 480, "red", 88.5));
                
        return dishesList;
    }
}
