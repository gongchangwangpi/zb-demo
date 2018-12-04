package com.jdksource.java8;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author zhangbo
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Dishes {

    /**
     * 菜肴名称
     */
    private String name;
    /**
     * 热量，卡路里
     */
    private int calories;
    /**
     * 菜肴颜色
     */
    private String color;
    /**
     * 价格
     */
    private double price;

    @Override
    public String toString() {
        return "Dishes{" +
                "name='" + name + '\'' +
                '}';
    }
}
