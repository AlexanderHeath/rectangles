package org.example;

import java.util.HashSet;
import java.util.Set;

public class Rectangle {

    private final double left;
    private final double right;
    private final double top;
    private final double bottom;
    private final double width;
    private final double height;
    private final Point[] points;

    /**
     * TODO a – first corner point in the rectangle (opposite of b) b – second corner point in the rectangle (opposite of a)
     *
     * @param a
     * @param b
     */
    public Rectangle(Point a, Point b) {
        if (a.getX() == b.getX() || a.getY() == b.getY())
            throw new IllegalArgumentException(String.format("Points cannot form a valid rectangle %s %s", a, b));
        //vertical bounds
        left = Math.min(a.getX(), b.getX());
        right = Math.max(a.getX(), b.getX());
        //horizontal bounds
        bottom = Math.min(a.getY(), b.getY());
        top = Math.max(a.getY(), b.getY());
        width = right - left;
        height = top - bottom;
        points = new Point[]{
                new Point(left, top),
                new Point(right, top),
                new Point(right, bottom),
                new Point(left, bottom)
        };
    }

    public Point[] getPoints() {
        return points.clone();
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    /**
     * It is reflexive: for any non-null reference value x, x.equals(x) should return true.
     * It is symmetric: for any non-null reference values x and y, x.equals(y) should return true if and only if y.equals(x) returns true.
     * It is transitive: for any non-null reference values x, y, and z, if x.equals(y) returns true and y.equals(z) returns true, then x.equals(z) should return true.
     * It is consistent: for any non-null reference values x and y, multiple invocations of x.equals(y) consistently return true or consistently return false, provided no information used in equals comparisons on the objects is modified.
     * For any non-null reference value x, x.equals(null) should return false.
     *
     * @param other
     * @return
     */
    //TODO endpoint intersection?
    public Set<Point> getIntersection(Rectangle other) {
        Set<Point> intersections = new HashSet<>();
        if (other == null || this.equals(other)) return intersections;
//        if (this.contains(other) || other.contains(this)) return intersections;
        setIntersections(this, other, intersections);
        setIntersections(other, this, intersections);



        //call above again with other

        return intersections;
    }

    private void setIntersections(Rectangle rec1, Rectangle rec2, Set<Point> intersections) {
        Point r1TopLeft = rec1.points[0];
        Point r1BottomLeft = rec1.points[3];
        Point r1TopRight = rec1.points[1];
        Point r1BottomRight = rec1.points[2];

        Point r2BottomLeft = rec2.points[3];
        Point r2BottomRight = rec2.points[2];
        Point r2TopLeft = rec2.points[0];
        Point r2TopRight = rec2.points[1];
        //left intersects with bottom
        if (hasVerticalHorizontalIntersection(r1TopLeft, r1BottomLeft, r2BottomLeft, r2BottomRight)){
            //intersection is left x bottom y
            intersections.add(new Point(r1TopLeft.getX(), r2BottomLeft.getY()));
        }
        //left intersects with top
        if (hasVerticalHorizontalIntersection(r1TopLeft, r1BottomLeft, r2TopLeft, r2TopRight)){
            //intersection is left x top y
            intersections.add(new Point(r1TopLeft.getX(), r2TopLeft.getY()));
        }
        //right intersects with top
        if (hasVerticalHorizontalIntersection(r1TopRight, r1BottomRight, r2TopLeft, r2TopRight)){
            //intersection is right x top y
            intersections.add(new Point(r1TopRight.getX(), r2TopLeft.getY()));
        }
        //right intersects with bottom
        if (hasVerticalHorizontalIntersection(r1TopRight, r1BottomRight, r2BottomLeft, r2BottomRight)){
            //intersection is right x bottom y
            intersections.add(new Point(r1TopRight.getX(), r2BottomLeft.getY()));
        }
    }
    //does left intersect with other bottom?
    //is left x between other x's and other bottom y between  left ys?
    //yes? intersection is left x other bottom y
    //does left intersect with other top?
    //is left x between other top x's & other top y between left y's?
    //yes? intersection is left x other top y
    //does right intersect with other top?
    //is right x between other top x's & other top y between right y's?
    //yes? intersection is right x other top y
    //does right intersect with other bottom?
    //right x between bottom x's && bottom y between right y's
    //yes? intersection is right x bot y

    private boolean hasVerticalHorizontalIntersection(Point vertP1, Point vertP2, Point horizP1, Point horizP2) {
        //If vertical x is between the horizontal x's and the horizontal y is between the vertical x's,
        // there is an intersection
        return (isBetween(horizP1.getX(), horizP2.getX(), vertP1.getX()) &&
                isBetween(vertP1.getY(), vertP2.getY(), horizP1.getY()));
    }

    //not symmetric
    public boolean contains(Rectangle other) {
        if (other == null || this.equals(other)) return false;
        return (other.left >= this.left && other.right <= this.right &&
                other.bottom >= this.bottom && other.top <= this.top);
    }

    //TODO less comparisons for isAdj
    public boolean isAdjacentTo(Rectangle other) {
        if (other == null) return false;
        if (this.equals(other)) return false;
        if (right == other.left || left == other.right) {
            return (isProper(top, bottom, other.top, other.bottom) ||
                    isSubLine(top, bottom, other.top, other.bottom) ||
                    isPartial(top, bottom, other.top, other.bottom));
        }
        if (top == other.bottom || bottom == other.top) {
            return (isProper(left, right, other.left, other.right) ||
                    isSubLine(left, right, other.left, other.right) ||
                    isPartial(left, right, other.left, other.right));
        }
        return false;
    }

    private boolean isProper(double side1, double side2, double otherSide1, double otherSide2) {
        return (side1 == otherSide1 && side2 == otherSide2);
    }

    private boolean isSubLine(double side1, double side2, double otherSide1, double otherSide2) {
        //this is sub-line of other or other is a sub-line of this
        //TODO currently will return true for proper adjacency
        return (side1 <= otherSide1 && side2 >= otherSide2) ||
                (side1 >= otherSide1 && side2 <= otherSide2);
    }

    private boolean isPartial(double side1, double side2, double otherSide1, double otherSide2) {
        return (isBetween(side1, side2, otherSide1) ^ isBetween(side1, side2, otherSide2)) ||
                (isBetween(otherSide1, otherSide2, side1) ^ isBetween(otherSide1, otherSide2, side2));
    }

    /**
     * Checks if c is in between a and b, exclusively
     *
     * @param a
     * @param b
     * @param c
     * @return
     */
    private boolean isBetween(double a, double b, double c) {
        return (a < c && c < b) || (b < c && c < a);
    }

    //TODO endpoint adjacency?

    //TODO EQUALS


    @Override
    public String toString() {
        return "Rectangle{}";
    }
}
