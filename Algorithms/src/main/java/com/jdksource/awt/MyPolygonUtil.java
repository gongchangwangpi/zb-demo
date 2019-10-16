package com.jdksource.awt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <span style="font-family: Arial; font-size: 14px; line-height: 26px;">点和线的一些公用方法</span><br/>
 *
 * @author liuZhiwei
 * 2016年8月6日 下午3:48:38
 */
@Slf4j
public class MyPolygonUtil {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Point implements Serializable {
        private double x;
        private double y;

        @Override
        public String toString() {
            return "(" + x + ", " + y + ")";
        }
    }

    public static List<Point> randomPointInPolygon(List<Point> polygon, int count) {
        if (polygon == null || polygon.size() < 3) {
            return new ArrayList<>();
        }

        // 先算出中心点
        Point center = calcCenterPoint(polygon);

        boolean centerIn = isPointInPolygon(center, polygon);
        if (centerIn) {
            return get(center, polygon, polygon, count);
        }

        int size = polygon.size();
        int three = 3;
        int i = 0;

        List<Point> result = new ArrayList<>();

        while (i < size - three) {
            List<Point> tempPolygon = polygon.subList(i, i + three);
            Point centerPoint = calcCenterPoint(tempPolygon);
            if (isPointInPolygon(centerPoint, polygon)) {
                return get(centerPoint, tempPolygon, polygon, count);
            } else {
                i++;
            }
        }

        while (result.size() <= count) {
            // 填充多边形顶点
            result.add(polygon.get(RandomUtils.nextInt(0, polygon.size())));
        }

        return result;
    }

    private static List<Point> get(Point center, List<Point> tempPolygon, List<Point> polygon, int count) {
        List<Point> result = new ArrayList<>();

        List<Double> pxList = tempPolygon.stream().map(Point::getX).collect(Collectors.toList());
        List<Double> pyList = tempPolygon.stream().map(Point::getY).collect(Collectors.toList());
        pxList.add(center.x);
        pyList.add(center.y);
        pxList.sort(Comparator.comparing(Double::doubleValue));
        pyList.sort(Comparator.comparing(Double::doubleValue));
        int ix = pxList.indexOf(center.x);
        int iy = pyList.indexOf(center.y);
        // 中心点距离最近的坐标
        Double randomMinx = pxList.get(ix - 1);
        Double randomMaxx = pxList.get(ix + 1);
        Double randomMiny = pyList.get(iy - 1);
        Double randomMaxy = pyList.get(iy + 1);

        result.add(center);

        for (int i = 0; i < count - 1; i++) {
            while (true) {
                double randomX = RandomUtils.nextDouble(randomMinx, randomMaxx);
                double randomY = RandomUtils.nextDouble(randomMiny, randomMaxy);
                Point randomPoint = new Point(randomX, randomY);
                if (isPointInPolygon(randomPoint, polygon)) {
                    result.add(randomPoint);
                    break;
                }
            }
        }
        return result;
    }

    public static Point calcCenterPoint(List<Point> polygon) {
        double centerX = 0, centerY = 0;
        for (Point point : polygon) {
            centerX += point.x;
            centerY += point.y;
        }
        centerX = centerX / polygon.size();
        centerY = centerY / polygon.size();
        return new Point(centerX, centerY);
    }

    /**
     * 是否有 横断<br/>
     * 参数为四个点的坐标
     *
     * @param p1
     * @param p2
     * @param p3
     * @param p4
     * @return
     */
    public static boolean isIntersect(Point p1, Point p2, Point p3, Point p4) {
        boolean flag = false;
        double d = (p2.x - p1.x) * (p4.y - p3.y) - (p2.y - p1.y) * (p4.x - p3.x);
        if (d != 0) {
            double r = ((p1.y - p3.y) * (p4.x - p3.x) - (p1.x - p3.x) * (p4.y - p3.y)) / d;
            double s = ((p1.y - p3.y) * (p2.x - p1.x) - (p1.x - p3.x) * (p2.y - p1.y)) / d;
            if ((r >= 0) && (r <= 1) && (s >= 0) && (s <= 1)) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 目标点是否在目标边上边上<br/>
     *
     * @param p0 目标点
     * @param p1 目标线的起点(终点)
     * @param p2 目标线的终点(起点)
     * @return
     */
    public static boolean isPointOnLine(Point p0, Point p1, Point p2) {
        boolean flag = false;
        // 无限小的正数
        double ESP = 1e-9;
        if ((Math.abs(multiply(p0, p1, p2)) < ESP) && ((p0.x - p1.x) * (p0.x - p2.x) <= 0)
                && ((p0.y - p1.y) * (p0.y - p2.y) <= 0)) {
            flag = true;
        }
        return flag;
    }

    public static double multiply(Point p0, Point p1, Point p2) {
        return ((p1.x - p0.x) * (p2.y - p0.y) - (p2.x - p0.x) * (p1.y - p0.y));
    }

    /**
     * 判断目标点是否在多边形内(由多个点组成)<br/>
     *
     * @param p        目标点
     * @param polygon 多边形的坐标集合
     * @return
     */
    public static boolean isPointInPolygon(Point p, List<Point> polygon) {
        boolean isInside = false;
        double ESP = 1e-9;
        int count = 0;

        // 辅助生成一条p点与X轴（纬度）的平行线
        Point point = new Point(p.x + 100, p.y);

        for (int i = 0; i < polygon.size() - 1; i++) {

            Point point1 = polygon.get(i);
            Point point2 = polygon.get(i + 1);
            double cy1 = point1.y;
            double cy2 = point2.y;

            //如果目标点在任何一条线上
            if (isPointOnLine(p, point1, point2)) {
                return true;
            }
            //如果线段的长度无限小(趋于零)那么这两点实际是重合的，不足以构成一条线段
            if (Math.abs(cy2 - cy1) < ESP) {
                continue;
            }
            //第一个点是否在以目标点为基础衍生的平行纬度线
            if (isPointOnLine(point1, p, point)) {
                //第二个点在第一个的下方,靠近赤道纬度为零(最小纬度)
                if (cy1 > cy2) {
                    count++;
                }
            }
            //第二个点是否在以目标点为基础衍生的平行纬度线
            else if (isPointOnLine(point2, p, point)) {
                //第二个点在第一个的上方,靠近极点(南极或北极)纬度为90(最大纬度)
                if (cy2 > cy1) {
                    count++;
                }
            }
            //由两点组成的线段是否和以目标点为基础衍生的平行纬度线相交
            else if (isIntersect(point1, point2, p, point)) {
                count++;
            }
        }
        if (count % 2 == 1) {
            isInside = true;
        }

        return isInside;
    }
}