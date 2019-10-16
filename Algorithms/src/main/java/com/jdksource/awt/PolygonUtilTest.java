package com.jdksource.awt;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.averagingDouble;

/**
 * @author zhangbo
 * @date 2019-10-12
 */
public class PolygonUtilTest {

    //[{
//        "x": 1.5,
//                "y": 1.390625,
//                "index": 0
//    }, {
//        "x": 276.5,
//                "y": 2.390625,
//                "index": 1
//    }, {
//        "x": 294.5,
//                "y": 317.390625,
//                "index": 2
//    }, {
//        "x": 3.5,
//                "y": 316.390625,
//                "index": 3
//    }]

    public static void main(String[] args) {

        List<Double> pxList = Arrays.asList(1.5, 276.5, 294.5,  3.5);
        List<Double> pyList = Arrays.asList(1.390625, 2.390625, 317.390625, 316.390625);

        boolean inPolygon = PolygonUtil.isPointInPolygon(126, 39, pxList, pyList);
        System.out.println(inPolygon);

        Double avgX = pxList.stream().collect(averagingDouble(Double::doubleValue));
        Double avgY = pyList.stream().collect(averagingDouble(Double::doubleValue));

        System.out.println(avgX); // 332.8
        System.out.println(avgY); // 84.46

        inPolygon = PolygonUtil.isPointInPolygon(avgX, avgY, pxList, pyList);
        System.out.println(inPolygon);
    }

}
