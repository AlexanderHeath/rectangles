package org.example;

import java.util.*;

/**
 * Representation of a Rectangle. This implementation does not support rectangles with rotation. That is to say, this
 * rectangle is composed of horizontal and vertical lines without slope.
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
        //vertical boundaries
        leftBound = Math.min(a.x(), b.x());
        rightBound = Math.max(a.x(), b.x());
        //horizontal boundaries
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
     * Determines whether two rectangles have one or more intersecting lines and returns a list of the points of
     * intersection.
     * This is method is symmetric. For any non-null reference values x and y, x.getIntersection(y) should return the
     * same result set as y.getIntersection(x).
     * @param other Rectangle with potentially intersecting line segments.
     * @return Set of intersection points or an empty Set if the rectangles do not intersect.
     */
    public Set<Point> getIntersection(Rectangle other) {
        Set<Point> intersections = new HashSet<>();
        if (other == null || this.equals(other)) return intersections;
        //check if the verticals of this intersect with the horizontals of other
        intersections.addAll(getIntersectionPoints(this, other));
        //check if other's verticals intersect with the horizontals of this
        intersections.addAll(getIntersectionPoints(other, this));
        return intersections;
    }

    /**
     * Returns the intersection points of the vertical segments of rec1 and the horizontal segments of rec2
     *
     * @param rec1 Rectangle 1
     * @param rec2 Rectangle 2
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
            //the point of intersection will be the vertical x and the horizontal y
            return Optional.of(new Point(vertical.point1().x(), horizontal.point1().y()));
        }
        return Optional.empty();
    }

    private boolean hasIntersection(LineSegment vertical, LineSegment horizontal) {
        //There is an intersection if vertical x is between the horizontal x's and
        // the horizontal y is between the vertical x's
        return (isBetween(horizontal.point1().x(), horizontal.point2().x(), vertical.point1().x()) &&
                isBetween(vertical.point1().y(), vertical.point2().y(), horizontal.point1().y()));
    }

    /**
     * Determine if this rectangle wholly contains another rectangle.
     * This method is not symmetric. If x.contains(y) is true then y.contains(x) should be false. However, if
     * x.contains(y) is false y.contains(x) can also be false.
     * @param other Rectangle
     * @return true if this contains other, false otherwise.
     */
    public boolean contains(Rectangle other) {
        if (other == null || this.equals(other)) return false;
        return (other.leftBound >= leftBound &&
                other.rightBound <= rightBound &&
                other.bottomBound >= bottomBound &&
                other.topBound <= topBound);
    }

    /**
     * Detect whether two rectangles are adjacent. Adjacency is defined as the sharing of at least one side. Side
     * sharing may be proper, sub-line, or partial.
     * This is method is symmetric. For any non-null reference values x and y, x.isAdjacentTo(y) should return the
     * same result as y.isAdjacentTo(x).
     * @param other Rectangle
     * @return true If the rectangles share adjacent sides, false otherwise.
     */
    public boolean isAdjacentTo(Rectangle other) {
        if (other == null) return false;
        if (this.equals(other)) return false;
        return hasProperAdjacency(this, other) || hasSubLineAdjacency(this, other) || hasPartialAdjacency(this, other);
    }

    /**
     * Detects and returns the Adjacency shared by the two rectangles. Adjacency is defined as the sharing of at least
     * one side. Side sharing may be proper, sub-line, or partial.
     * This is method is symmetric. For any non-null reference values x and y, x.isAdjacentTo(y) should return the
     * same result as y.isAdjacentTo(x).
     * @param other Rectangle
     * @return Optional of Adjacency if the rectangles are adjacent, empty otherwise.
     */
    public Optional<Adjacency> getAdjacency(Rectangle other) {
        if (other == null) return Optional.empty();
        if (this.equals(other)) return Optional.empty();
        if (hasProperAdjacency(this, other)) return Optional.of(new Adjacency(AdjacencyType.PROPER));
        if (hasSubLineAdjacency(this, other)) return Optional.of(new Adjacency(AdjacencyType.SUBLINE));
        if (hasPartialAdjacency(this, other)) return Optional.of(new Adjacency(AdjacencyType.PARTIAL));
        return Optional.empty();
    }

    private boolean hasProperAdjacency(Rectangle rec1, Rectangle rec2) {
        //if the two rectangles share right and left boundaries, check if their top & bottom boundaries are the same
        boolean hasVert = (rec1.rightBound == rec2.leftBound || rec1.leftBound == rec2.rightBound) &&
                (rec1.topBound == rec2.topBound && rec1.bottomBound == rec2.bottomBound);
        //if the two rectangles share top & bottom boundaries, check if their right & left boundaries are the same
        boolean hasHoriz = (rec1.topBound == rec2.bottomBound || rec1.bottomBound == rec2.topBound) &&
                (rec1.leftBound == rec2.leftBound && rec1.rightBound == rec2.rightBound);
        return hasVert || hasHoriz;
    }

    private boolean hasSubLineAdjacency(Rectangle rec1, Rectangle rec2) {
        //TODO this logic will return true for proper adjacency as well as sub-line
        //if the two rectangles share right & left boundaries, check if the top & bottom boundaries are within each other
        boolean hasVert = (rec1.rightBound == rec2.leftBound || rec1.leftBound == rec2.rightBound) &&
                ((rec1.topBound <= rec2.topBound && rec1.bottomBound >= rec2.bottomBound) ||
                        (rec1.topBound >= rec2.topBound && rec1.bottomBound <= rec2.bottomBound));
        //if the two rectangles share top & bottom boundaries, check if their right & left boundaries are within each other
        boolean hasHoriz = (rec1.topBound == rec2.bottomBound || rec1.bottomBound == rec2.topBound) &&
                ((rec1.leftBound <= rec2.leftBound && rec1.rightBound >= rec2.rightBound) ||
                        (rec1.leftBound >= rec2.leftBound && rec1.rightBound <= rec2.rightBound));
        return hasVert || hasHoriz;
    }

    private boolean hasPartialAdjacency(Rectangle rec1, Rectangle rec2) {
        //if the two rectangles share right & left boundaries, check if there is overlap between the top or bottom boundaries exclusively
        boolean hasVert = (rec1.rightBound == rec2.leftBound || rec1.leftBound == rec2.rightBound) &&
                ((isBetween(rec1.topBound, rec1.bottomBound, rec2.topBound) ^ isBetween(rec1.topBound, rec1.bottomBound, rec2.bottomBound)) ||
                (isBetween(rec2.topBound, rec2.bottomBound, rec1.topBound) ^ isBetween(rec2.topBound, rec2.bottomBound, rec1.bottomBound)));
        //if the two rectangles share top & bottom boundaries, check if there is overlap between the right or left boundaries exclusively
        boolean hasHoriz = (rec1.topBound == rec2.bottomBound || rec1.bottomBound == rec2.topBound) &&
                ((isBetween(leftBound, rightBound, rec2.leftBound) ^ isBetween(leftBound, rightBound, rec2.rightBound)) ||
                (isBetween(rec2.leftBound, rec2.rightBound, leftBound) ^ isBetween(rec2.leftBound, rec2.rightBound, rightBound)));
        return hasVert || hasHoriz;
    }

    /**
     * Checks if one number is between two others, exclusive
     *
     * @param a boundary 1
     * @param b boundary 2
     * @param c between candidate
     * @return tru if c is between a & b
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
