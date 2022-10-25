package org.example;

import org.apache.commons.geometry.euclidean.twod.LineConvexSubset;
import org.apache.commons.geometry.euclidean.twod.Vector2D;
import org.apache.commons.geometry.euclidean.twod.shape.Parallelogram;
import org.apache.commons.numbers.core.Precision;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class Rectangle {


    private final double left;
    private final double right;
    private final double top;
    private final double bottom;

    /**
     *
     * TODO a – first corner point in the rectangle (opposite of b) b – second corner point in the rectangle (opposite of a)
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
//        double width = right - left;
//        double height = bottom - top;
        //create points
        //set segments

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
        Set<Point> points = new HashSet<>();
        if (other == null || this.equals(other)) return points;
        /**
         * check if has intersection by seeing if there is a segment inside but not contained
         * x has to be within x's
         * one y has to be within others & one y outside
         *
         * horizontal & vertical lines will intersect.. (same lines will only overlap)
         *
         * x's of a segment between the b
         * y's of b between a
         * or reverse we have
         *
         *
         * if vertical and horizontal intersect it will be at (vertical, horizontal)
         * ex. left & top intersect. intersection point will be (left, top)
         *
         */
        if (this.contains(other) || other.contains(this)) return points;
        if (isBetween(left, right, other.left)) {
            if (isBetween(top, bottom, other.top)) points.add(new Point(other.left, bottom));
            if (isBetween(top, bottom, other.bottom)) points.add(new Point(other.left, top));
            if (isBetween(other.top, other.bottom, top)) points.add(new Point(other.left, top));
            if (isBetween(other.top, other.bottom, bottom)) points.add(new Point(other.left, bottom));
        }
        if (isBetween(left, right, other.right)) {
            if (isBetween(top, bottom, other.top)) points.add(new Point(other.right, bottom));
            if (isBetween(top, bottom, other.bottom)) points.add(new Point(other.right, top));
            if (isBetween(other.top, other.bottom, top)) points.add(new Point(other.right, top));
            if (isBetween(other.top, other.bottom, bottom)) points.add(new Point(other.right, bottom));
        }
        if (isBetween(other.left, other.right, left)) {
            if (isBetween(other.top, other.bottom, top)) points.add(new Point(left, other.bottom));
            if (isBetween(other.top, other.bottom, bottom)) points.add(new Point(left, other.top));
            if (isBetween(top, bottom, other.top)) points.add(new Point(left, other.top));
            if (isBetween(top, bottom, other.bottom)) points.add(new Point(left, other.bottom));
        }
        if (isBetween(other.left, other.right, right)) {
            if (isBetween(other.top, other.bottom, top)) points.add(new Point(right, other.bottom));
            if (isBetween(other.top, other.bottom, bottom)) points.add(new Point(right, other.top));
            if (isBetween(top, bottom, other.top)) points.add(new Point(right, other.top));
            if (isBetween(top, bottom, other.bottom)) points.add(new Point(right, other.bottom));
        }
        return points;
    }

    //not symmetric
    public boolean contains(Rectangle other) {
        if (other == null || this.equals(other)) return false;
        return (other.left >= this.left && other.right <= this.right &&
                other.bottom >= this.bottom && other.top <= this.top);
    }

    //TODO less comparisons for hasAdj
    public boolean hasAdjacency(Rectangle other) {
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
     * @param a
     * @param b
     * @param c
     * @return
     */
    private boolean isBetween(double a, double b, double c) {
        return (a < c && c < b) || (b < c && c < a);
    }

    //TODO endpoint adjacency?
//    public Optional<Adjacency> getAdjacency(Rectangle other) {
//        if (other == null) return Optional.empty();
//        if (this.equals(other)) return Optional.of(new Adjacency(AdjacencyType.PROPER));
//        List<LineConvexSubset> subs = pGram.getBoundaryPaths().get(0).getElements();
//        List<LineConvexSubset> otherSubs = other.pGram.getBoundaryPaths().get(0).getElements();
//        boolean hasPartial = false;
//        for (LineConvexSubset sub : subs) {
//            for (LineConvexSubset otherSub : otherSubs) {
//                //if the two segments are the same it is proper adjacency
//                if (subsetEquals(sub, otherSub)) return Optional.of(new Adjacency(AdjacencyType.PROPER));
//                boolean hasStart = otherSub.contains(sub.getStartPoint());
//                boolean hasEnd = otherSub.contains(sub.getEndPoint());
//                boolean hasOtherStart = sub.contains(otherSub.getStartPoint());
//                boolean hasOtherEnd = sub.contains((otherSub.getEndPoint()));
//                //if one of the segments is entirely within the other is it sub-line adjacency
//                if ((hasStart && hasEnd) || (hasOtherStart && hasOtherEnd))
//                    return Optional.of(new Adjacency(AdjacencyType.SUBLINE));
//                //if one or more of the segments is partially within the other it is partial adjacency TODO
//                //one on one side and one on other side but not both is partial adjacency
//                if ((hasStart || hasEnd) && (hasOtherStart || hasOtherEnd))
//                    hasPartial = true;
//            }
//        }
//        return hasPartial ? Optional.of(new Adjacency(AdjacencyType.PARTIAL)) : Optional.empty();
//    }

//    private boolean subsetEquals(LineConvexSubset subset1, LineConvexSubset subset2) {
//        return (subset1.getStartPoint().equals(subset2.getStartPoint()) && subset1.getEndPoint().equals(subset2.getEndPoint()) ||
//                subset1.getStartPoint().equals(subset2.getEndPoint()) && subset1.getEndPoint().equals(subset2.getStartPoint()));
//    }

//TODO EQUALS
}
