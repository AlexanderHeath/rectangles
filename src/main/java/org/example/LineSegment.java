package org.example;

import java.util.Objects;

public record LineSegment(Point point1, Point point2) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LineSegment that = (LineSegment) o;
        return Objects.equals(point1, that.point1) && Objects.equals(point2, that.point2);
    }

}
