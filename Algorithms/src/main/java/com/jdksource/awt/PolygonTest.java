package com.jdksource.awt;

import java.awt.*;

/**
 * 多边形
 *
 * @author zhangbo
 * @date 2019-10-11
 */
public class PolygonTest {

    public static void main(String[] args) throws InterruptedException {

        int[] x = {0, 10, 10, 0};
        int[] y = {0, 0, 10, 10};

        Polygon polygon = new Polygon(x, y, 4);

//        System.out.println(polygon);

        System.out.println(polygon.contains(9.9, 1));

        Rectangle rectangle = polygon.getBounds();
        System.out.println(rectangle.getCenterX());
        System.out.println(rectangle.getCenterY());


        Point point1 = new Point(0, 0);
        Point point2 = new Point(10, 10);


        System.out.println(point1.distance(point2));

    }

}
