//package com.jdksource.awt;
//
//import com.snatik.polygon.Point;
//import com.snatik.polygon.Polygon;
//
///**
// * {@see https://github.com/sromku/polygon-contains-point}
// *
// * @author zhangbo
// * @date 2019-10-11
// */
//public class GitPolygonTest {
//
//    public static void main(String[] args) {
//        Polygon polygon = Polygon.Builder()
//                .addVertex(new Point(296f, 51.26f))
//                .addVertex(new Point(282f, 96.26f))
//                .addVertex(new Point(309f, 107.26f))
//                .addVertex(new Point(261f, 96.26f))
//                .addVertex(new Point(416f, 71.26f))
//                .build();
//
//        boolean contains = polygon.contains(new Point(283f, 96.26f));
//        System.out.println(contains);
//
//        contains = polygon.contains(new Point(332.8f, 84.46f));
//        System.out.println(contains);
//    }
//
//}
