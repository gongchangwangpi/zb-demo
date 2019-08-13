package com.test.error.lombok;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 区域
 *
 * @author zhangbo
 * @date 2019-08-13
 */
@Data
//@Getter
//@Setter
public class Region {

    private Integer code;

    private String name;

    private Integer parentCode;

    private Set<Region> children = new HashSet<>();

    public Region() {
    }

    public Region(Integer code, String name, Integer parentCode) {
        this.code = code;
        this.name = name;
        this.parentCode = parentCode;
    }

    public static void main(String[] args) {
        Region r1 = new Region(510000, "四川省", 0);
        Region r11 = new Region(510100, "成都市", 510000);

        System.out.println(r1.hashCode());

        r1.getChildren().add(r11);

        System.out.println(r1.hashCode());
    }

}
