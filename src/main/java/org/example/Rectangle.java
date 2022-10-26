package org.example;

import java.util.*;

/**
 * Representation of a Rectangle. This implementation does not support rectangles with rotation. That is to say
 */
public class Rectangle {

    private final double leftBound;
    private final double rightBound;
    private final double topBound;
    private final double bottomBound;
    private final double width;
    private final double height;

    private final LineSegment leftSeg;
    private final LineSegment rightSeg;
    private final LineSegment topSeg;
    private final LineSegment bottomSeg;


    /**
     * Create a rectangle from two opposite corner points. The constructor will throw an IllegalArgumentException
     * if a rectangle cannot be created from the points.
     *
     * @param a First corner of the rectangle (opposite of b)
     * @param b Second corner of the rectangle (opposite of a)
     */
    public Rectangle(Point a, Point b) {
        if (a.x() == b.x() || a.y() == b.y())
            throw new IllegalArgumentException(String.format("Points cannot form a valid rectangle %s %s", a, b));
        //vertical bounds
        leftBound = Math.min(a.x(), b.x());
        rightBound = Math.max(a.x(), b.x());
        //horizontal bounds
        bottomBound = Math.min(a.y(), b.y());
        topBound = Math.max(a.y(), b.y());
        width = rightBound - leftBound;
        height = topBound - bottomBound;
        Point topLeft = new Point(leftBound, topBound);
        Point bottomLeft = new Point(leftBound, bottomBound);
        Point topRight = new Point(rightBound, topBound);
        Point bottomRight = new Point(rightBound, bottomBound);
        leftSeg = new LineSegment(bottomLeft, topLeft);
        rightSeg = new LineSegment(bottomRight, topRight);
        topSeg = new LineSegment(topLeft, topRight);
        bottomSeg = new LineSegment(bottomLeft, bottomRight);
    }

    /**
     * Get the points representing the four corners of the rectangle.
     * @return Array of points.
     */
    public Point[] getPoints() {
        return new Point[]{leftSeg.point1(), leftSeg.point2(), rightSeg.point1(), rightSeg.point2()};
    }

    /**
     * Get the width of the rectangle.
     * @return width value
     */
    public double getWidth() {
        return width;
    }

    /**
     * Get the height of the rectangle.
     * @return height value
     */
    public double getHeight() {
        return height;
    }

    /**
     *
     * This is method is symmetric. For any non-null reference values x and y, x.getIntersection(y) should return the
     * same result set as y.getIntersection(x).
     * @param other Rectangle with potentially intersecting line segments.
     * @return Set of intersection points.
     */
    public Set<Point> getIntersection(Rectangle other) {
        Set<Point> intersections = new HashSet<>();
        if (other == null || this.equals(other)) return intersections;
//        if (this.contains(other) || other.contains(this)) return intersections; TODO test case
        //check if the verticals of this intersect with the horizontals of other
        intersections.addAll(getIntersectionPoints(this, other));
        //check if other's verticals intersect with the horizontals of this
        intersections.addAll(getIntersectionPoints(other, this));
        return intersections;
    }

    /**
     * Returns the intersection points of the vertical segments of rec1 and the horizontal segments of rec2
     *
     * @param rec1
     * @param rec2
     * @return Set of points
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
            return Optional.of(new Point(vertical.point1().x(), horizontal.point1().y()));
        }
        return Optional.empty();
    }

    private boolean hasIntersection(LineSegment vertical, LineSegment horizontal) {
        //If vertical x is between the horizontal x's and the horizontal y is between the vertical x's,
        // there is an intersection
        return (isBetween(horizontal.point1().x(), horizontal.point2().x(), vertical.point1().x()) &&
                isBetween(vertical.point1().y(), vertical.point2().y(), horizontal.point1().y()));
    }

    /**
     *
     * This method is not symmetric. x.contains(y) should not return the same result as y.contains(x).
     * @param other
     * @return true if this contains other, false otherwise
     */
    public boolean contains(Rectangle other) {
        if (other == null || this.equals(other)) return false;
        return (other.leftSeg.point1().x() >= this.leftSeg.point1().x() &&
                other.rightSeg.point1().x() <= this.rightSeg.point1().x() &&
                other.bottomSeg.point1().y() >= this.bottomSeg.point1().y() &&
                other.topSeg.point1().y() <= this.topSeg.point1().y());
    }

    /**
     * This is method is symmetric. For any non-null reference values x and y, x.isAdjacentTo(y) should return the
     * same result as y.isAdjacentTo(x).
     * @param other
     * @return true If the rectangles share adjacent sides, false otherwise.
     */
    public boolean isAdjacentTo(Rectangle other) {
        if (other == null) return false;
        if (this.equals(other)) return false;
        return hasProperAdjacency(this, other) || hasSubLineAdjacency(this, other) || hasPartialAdjacency(this, other);
    }

//    private boolean isProper(double side1, double side2, double otherSide1, double otherSide2) {
//        return (side1 == otherSide1 && side2 == otherSide2);
//    }

    private boolean hasProperAdjacency(Rectangle rec1, Rectangle rec2) {
        boolean hasVert = (rec1.rightBound == rec2.leftBound || rec1.leftBound == rec2.rightBound) &&
                (rec1.topBound == rec2.topBound && rec1.bottomBound == rec2.bottomBound);
        boolean hasHoriz = (rec1.topBound == rec2.bottomBound || rec1.bottomBound == rec2.topBound) &&
                (rec1.leftBound == rec2.leftBound && rec1.rightBound == rec2.rightBound);
        return hasVert || hasHoriz;
    }

    private boolean hasSubLineAdjacency(Rectangle rec1, Rectangle rec2) {
        //rec1 is sub-line of rec2 or rec2 is a sub-line of rec1
        //TODO currently will return true for proper adjacency
        boolean hasVert = (rec1.rightBound == rec2.leftBound || rec1.leftBound == rec2.rightBound) &&
                ((rec1.topBound <= rec2.topBound && rec1.bottomBound >= rec2.bottomBound) ||
                        (rec1.topBound >= rec2.topBound && rec1.bottomBound <= rec2.bottomBound));

        boolean hasHoriz = (rec1.topBound == rec2.bottomBound || rec1.bottomBound == rec2.topBound) &&
                ((rec1.leftBound <= rec2.leftBound && rec1.rightBound >= rec2.rightBound) ||
                        (rec1.leftBound >= rec2.leftBound && rec1.rightBound <= rec2.rightBound));
        return hasVert || hasHoriz;
    }

    private boolean hasPartialAdjacency(Rectangle rec1, Rectangle rec2) {
        boolean hasVert = (rec1.rightBound == rec2.leftBound || rec1.leftBound == rec2.rightBound) &&
                ((isBetween(rec1.topBound, rec1.bottomBound, rec2.topBound) ^ isBetween(rec1.topBound, rec1.bottomBound, rec2.bottomBound)) ||
                (isBetween(rec2.topBound, rec2.bottomBound, rec1.topBound) ^ isBetween(rec2.topBound, rec2.bottomBound, rec1.bottomBound)));
        boolean hasHoriz = (rec1.topBound == rec2.bottomBound || rec1.bottomBound == rec2.topBound) &&
                ((isBetween(leftBound, rightBound, rec2.leftBound) ^ isBetween(leftBound, rightBound, rec2.rightBound)) ||
                (isBetween(rec2.leftBound, rec2.rightBound, leftBound) ^ isBetween(rec2.leftBound, rec2.rightBound, rightBound)));
        return hasVert || hasHoriz;
    }

    /**
     * Checks if c is in between a and b, exclusive
     *
     * @param a
     * @param b
     * @param c
     * @return
     */
    private boolean isBetween(double a, double b, double c) {
        return (a < c && c < b) || (b < c && c < a);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rectangle rectangle = (Rectangle) o;
        return leftSeg.equals(rectangle.leftSeg) && rightSeg.equals(rectangle.rightSeg) &&
                topSeg.equals(rectangle.topSeg) && bottomSeg.equals(rectangle.bottomSeg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(leftSeg, rightSeg, topSeg, bottomSeg);
    }

    @Override
    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(",");
        stringJoiner.add(leftSeg.point1().toString())
                .add(leftSeg.point2().toString())
                .add(rightSeg.point1().toString())
                .add(rightSeg.point2().toString());
        return stringJoiner.toString();
    }
}
