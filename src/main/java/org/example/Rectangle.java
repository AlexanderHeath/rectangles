package org.example;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class Rectangle {

    private final double left;
    private final double right;
    private final double top;
    private final double bottom;
    private final double width;
    private final double height;

    private final LineSegment leftSeg;
    private final LineSegment rightSeg;
    private final LineSegment topSeg;
    private final LineSegment bottomSeg;
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
        Point topLeft = new Point(left, top);
        Point bottomLeft = new Point(left, bottom);
        Point topRight = new Point(right, top);
        Point bottomRight = new Point(right, bottom);

        points = new Point[]{
                new Point(left, top),
                new Point(right, top),
                new Point(right, bottom),
                new Point(left, bottom)
        };
        leftSeg = new LineSegment(bottomLeft, topLeft);
        rightSeg = new LineSegment(bottomRight, topRight);
        topSeg = new LineSegment(topLeft, topRight);
        bottomSeg = new LineSegment(bottomLeft, bottomRight);
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
    public Set<Point> getIntersection(Rectangle other) {
        Set<Point> intersections = new HashSet<>();
        if (other == null || this.equals(other)) return intersections;
//        if (this.contains(other) || other.contains(this)) return intersections;
        //check if the verticals of this intersect with the horizontals of other
        intersections.addAll(getIntersectionPoints(this, other));
        //check if other's verticals intersect with the horizontals of this
        intersections.addAll(getIntersectionPoints(other, this));
        return intersections;
    }

    /**
     * Returns the intersection points of the vertical segments of rec1 and the horizontal segments of rec2
     * @param rec1
     * @param rec2
     * @return
     */
    private Set<Point> getIntersectionPoints(Rectangle rec1, Rectangle rec2) {
        Set<Point> intersections = new HashSet<>();
        getIntersectionPoint(rec1.leftSeg, rec2.bottomSeg).ifPresent(intersections::add);
        getIntersectionPoint(rec1.leftSeg, rec2.topSeg).ifPresent(intersections::add);
        getIntersectionPoint(rec1.rightSeg, rec2.bottomSeg).ifPresent(intersections::add);
        getIntersectionPoint(rec1.rightSeg, rec2.topSeg).ifPresent(intersections::add);
        return intersections;
    }

    private Optional<Point> getIntersectionPoint(LineSegment vertical, LineSegment horizontal) {
        if (hasIntersection(vertical, horizontal)) {
            return Optional.of(new Point(vertical.getPoint1().getX(), horizontal.getPoint1().getY()));
        }
        return Optional.empty();
    }

    private boolean hasIntersection(LineSegment vertical, LineSegment horizontal) {
        //If vertical x is between the horizontal x's and the horizontal y is between the vertical x's,
        // there is an intersection
        return (isBetween(horizontal.getPoint1().getX(), horizontal.getPoint2().getX(), vertical.getPoint1().getX()) &&
                isBetween(vertical.getPoint1().getY(), vertical.getPoint2().getY(), horizontal.getPoint1().getY()));
    }

    //not symmetric
//    public boolean contains(Rectangle other) {
//        if (other == null || this.equals(other)) return false;
//        return (other.left >= this.left && other.right <= this.right &&
//                other.bottom >= this.bottom && other.top <= this.top);
//    }
    public boolean contains(Rectangle other) {
        if (other == null || this.equals(other)) return false;
        return (other.leftSeg.getPoint1().getX() >= this.leftSeg.getPoint1().getX() &&
                other.rightSeg.getPoint1().getX() <= this.rightSeg.getPoint1().getX() &&
                other.bottomSeg.getPoint1().getY() >= this.bottomSeg.getPoint1().getY() &&
                other.topSeg.getPoint1().getY() <= this.topSeg.getPoint1().getY());
    }
    //TODO less comparisons for isAdj
//    public boolean isAdjacentTo(Rectangle other) {
//        if (other == null) return false;
//        if (this.equals(other)) return false;
//        if (right == other.left || left == other.right) {
//            return (isProper(top, bottom, other.top, other.bottom) ||
//                    isSubLine(top, bottom, other.top, other.bottom) ||
//                    isPartial(top, bottom, other.top, other.bottom));
//        }
//        if (top == other.bottom || bottom == other.top) {
//            return (isProper(left, right, other.left, other.right) ||
//                    isSubLine(left, right, other.left, other.right) ||
//                    isPartial(left, right, other.left, other.right));
//        }
//        return false;
//    }

    public boolean isAdjacentTo(Rectangle other) {
        if (other == null) return false;
        if (this.equals(other)) return false;

        //we share a left or right side
        //either top & bottom y's are same
        //or either other's top y is between this top & bottom y or other's bottom y is between this top & bottom y
        //or top y is between other's top & bottom y ||

        //right or left on same vertical line
        if (shareVertical(this, other)) {
            if (shareHorizontal(this, other)) return true; //(proper)

            if (topSeg.getPoint1().getY() == other.topSeg.getPoint1().getY() &&
                    bottomSeg.getPoint1().getY() == other.bottomSeg.getPoint1().getY())
                return true;



            if (isProper(bottomSeg.getPoint1().getY(), topSeg.getPoint1().getY(),
                    other.bottomSeg.getPoint1().getY(), other.topSeg.getPoint1().getY())) {
                return true;
            }

        }
        //left on same vertical line
        if (leftSeg.getPoint1().getX() == other.rightSeg.getPoint1().getX()) {
            if (leftSeg == rightSeg) return true;
            if (isBetween(leftSeg.getPoint1().getY(), leftSeg.getPoint1().getY(), other.topSeg.getPoint1().getY()) ||
                    isBetween(leftSeg.getPoint1().getY(), leftSeg.getPoint1().getY(), other.bottomSeg.getPoint1().getY())
            //check if same (proper)
            //if not
            //check if either top or bottom y within left y (partial, but will work for sub-line)
        }

        if (rightSeg.getPoint1().getX() == other.leftSeg.getPoint1().getX() ||
                leftSeg.getPoint1().getX() == other.rightSeg.getPoint1().getX()) {
            //on same vertical line
            //now need to check if there is any horizontal cross over
            return
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

    private boolean shareVertical(Rectangle rec1, Rectangle rec2) {
        return rec1.rightSeg.getPoint1().getX() == rec2.leftSeg.getPoint1().getX() ||
                rec1.leftSeg.getPoint1().getX() == rec2.rightSeg.getPoint1().getX();
    }

    private boolean shareHorizontal(Rectangle rec1, Rectangle rec2) {
        return rec1.topSeg.getPoint1().getX() == rec2.bottomSeg.getPoint1().getX() ||
                rec1.bottomSeg.getPoint1().getX() == rec2.topSeg.getPoint1().getX();
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
