package com.jdksource.awt;

import coords.Coordinates;
import geohash.GeoHashUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author zhangbo
 * @date 2019-10-12
 */
public class GeoHashUtilsTest {

    public static void main(String[] args) {

        List<Double> pxList = Arrays.asList(296d, 282d, 309d, 361d, 416d);
        List<Double> pyList = Arrays.asList(51.26, 96.26, 107.26, 96.26, 71.26);

        Coordinates[] polygon = {
                new Coordinates(296f, 51.26f),
                new Coordinates(282f, 96.26f),
                new Coordinates(309f, 107.26f),
                new Coordinates(261f, 96.26f),
                new Coordinates(416f, 71.26f),
        };

        boolean inside = GeoHashUtils.isInside(new Coordinates(283f, 96.26f), polygon);
        System.out.println(inside);

        inside = GeoHashUtils.isInside(new Coordinates(332.8f, 84.46f), polygon);
        System.out.println(inside);
    }

}
