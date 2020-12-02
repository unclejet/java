package com.uj.java8.lambda.test;

import java.util.Comparator;
import java.util.List;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

/**
 * @author ：UncleJet
 * @date ：Created in 2020/12/1 下午12:24
 * @description：
 */
public class Point {
    private final int x;
    private final int y;

    /**
     * 有些时候,你可以借助某个字段访问Lambda函数,这种情况,你可以利用这些字段,通过
     * 它们对封装在Lambda函数内的逻辑进行测试。比如,我们假设你在 Point类中添加了静态字段
     * compareByXAndThenY,通过该字段,使用方法引用你可以访问Comparator对象:
     */
    public final static Comparator<Point> compareByXAndThenY =
            comparing(Point::getX).thenComparing(Point::getY);


    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Point moveRightBy(int x) {
        return new Point(this.x + x, this.y);
    }

    /**
     * 但是Lambda的初衷是将一部分逻辑封装起来给另一个方法使用。从这个角度出发,你不应
     * 该将Lambda表达式声明为public,它们仅是具体的实现细节。相反,我们需要对使用Lambda表达式的方法进行测试。
     * @param points
     * @param x
     * @return
     */
    public static List<Point> moveAllPointsRightBy(List<Point> points, int x){
        return points.stream()
                .map(p -> new Point(p.getX() + x, p.getY()))
                .collect(toList());
    }

//    @Override
//    public boolean equals(Object obj) {
//        if (obj instanceof Point) {
//            return false;
//        }
//        Point point = (Point) obj;
//        return point.x == this.x && point.y == this.y;
//    }
}
