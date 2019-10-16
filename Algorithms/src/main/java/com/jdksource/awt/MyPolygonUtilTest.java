package com.jdksource.awt;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.averagingDouble;

/**
 * @author zhangbo
 * @date 2019-10-12
 */
public class MyPolygonUtilTest {

    public static void main(String[] args) {
//        List<Double> pxList = Arrays.asList(296d, 282d, 309d, 361d, 416d);
//        List<Double> pyList = Arrays.asList(51.26, 96.26, 107.26, 96.26, 71.26);
//
//        List<MyPolygonUtil.Point> polygon = new ArrayList<>();
//        for (int i = 0; i < pxList.size(); i++) {
//            MyPolygonUtil.Point point = new MyPolygonUtil.Point(pxList.get(i), pyList.get(i));
//            polygon.add(point);
//        }
//
//        boolean inPolygon = MyPolygonUtil.isPointInPolygon(new MyPolygonUtil.Point(283, 96.26), polygon);
//        System.out.println(inPolygon);
//
//        Double avgX = pxList.stream().collect(averagingDouble(Double::doubleValue));
//        Double avgY = pyList.stream().collect(averagingDouble(Double::doubleValue));
//
//        System.out.println(avgX); // 332.8
//        System.out.println(avgY); // 84.46
//
//        inPolygon = MyPolygonUtil.isPointInPolygon(new MyPolygonUtil.Point(avgX, avgY), polygon);
//        System.out.println(inPolygon);
//
//        List<MyPolygonUtil.Point> pointList = MyPolygonUtil.randomPointInPolygon(polygon, 5);
//        System.out.println(pointList);

        List<MyPolygonUtil.Point> polygon = new ArrayList<>();
        polygon.add(new MyPolygonUtil.Point(0, 0));
        polygon.add(new MyPolygonUtil.Point(0, 10));
        polygon.add(new MyPolygonUtil.Point(10, 10));
//        polygon.add(new MyPolygonUtil.Point(10, 0));

        List<MyPolygonUtil.Point> pointList = MyPolygonUtil.randomPointInPolygon(polygon, 5);
        System.out.println(pointList);

    }

}
