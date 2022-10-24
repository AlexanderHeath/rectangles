package org.example;

import java.util.Objects;

public class LineSegment {

    private final Point point1;
    private final Point point2;

    public LineSegment(Point point1, Point point2) {
        this.point1 = point1;
        this.point2 = point2;
    }

    public Point getPoint1() {
        return point1;
    }

    public Point getPoint2() {
        return point2;
    }


    public boolean contains(Point point) {
        /**
         * check if cross product of (b-a) & (c-a) is 0. this tells if the three points are aligned (colinear?)
         * if dot product of (b-a) & (c-a) is positive & less than square of the distance between a & b
         */
        return false;
    }

    public boolean contains(LineSegment segment) {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LineSegment that = (LineSegment) o;
        return Objects.equals(point1, that.point1) && Objects.equals(point2, that.point2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(point1, point2);
    }
}
