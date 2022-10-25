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

    private static final double DEFAULT_PRECISION = 1e-6;

    private Parallelogram pGram;

    private double left;
    private double right;
    private double top;
    private double bottom;

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

    public Rectangle(Point point1, Point point2, Point point3, Point point4) {
        /**
         * could assume points are specified in clockwise order & state in javadoc. then validate?
         * seg 1 perp to seg 2, seg 2 perp to seg 3, seg 3 perp to seg 4?
         *
         * order of vertices left most,
         *
         *
         * how to validate? parallel to segment with same length. perpendicular to segment with diff length
         */
        Precision.DoubleEquivalence precision = Precision.doubleEquivalenceOfEpsilon(DEFAULT_PRECISION);
        Vector2D vec1 = Vector2D.of(point1.getX(), point1.getY());
        Vector2D vec3 = Vector2D.of(point3.getX(), point3.getY());

        try {
            pGram = Parallelogram.axisAligned(vec1, vec3, precision);
        } catch (IllegalArgumentException e) {
            System.out.println(e);
            throw new IllegalArgumentException("Points did not form a valid rectangle", e);
        }
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
        List<LineConvexSubset> subsets = pGram.getBoundaryPaths().get(0).getElements();
        List<LineConvexSubset> otherSubsets = pGram.getBoundaryPaths().get(0).getElements();
        for (LineConvexSubset subset : subsets) {
            for (LineConvexSubset otherSubset : otherSubsets) {
                Vector2D inter = subset.intersection(otherSubset);
                if (inter != null) {
                    points.add(new Point(inter.getX(), inter.getY()));
                }
            }
        }
        return points;
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
         */

        //is left in between left & right ? yes is top between other top & bottom?
    }

    //not symmetric
    public boolean contains(Rectangle other) {
        if (other == null || this.equals(other)) return false;
        /**
         * both x's between other x's inclusive
         * both y's between other y inclusive
         */
        return (other.left >= this.left && other.right <= this.right &&
                other.bottom >= this.bottom && other.top <= this.top);
    }

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
    public Optional<Adjacency> getAdjacency(Rectangle other) {
        if (other == null) return Optional.empty();
        if (this.equals(other)) return Optional.of(new Adjacency(AdjacencyType.PROPER));
        List<LineConvexSubset> subs = pGram.getBoundaryPaths().get(0).getElements();
        List<LineConvexSubset> otherSubs = other.pGram.getBoundaryPaths().get(0).getElements();
        boolean hasPartial = false;
        for (LineConvexSubset sub : subs) {
            for (LineConvexSubset otherSub : otherSubs) {
                //if the two segments are the same it is proper adjacency
                if (subsetEquals(sub, otherSub)) return Optional.of(new Adjacency(AdjacencyType.PROPER));
                boolean hasStart = otherSub.contains(sub.getStartPoint());
                boolean hasEnd = otherSub.contains(sub.getEndPoint());
                boolean hasOtherStart = sub.contains(otherSub.getStartPoint());
                boolean hasOtherEnd = sub.contains((otherSub.getEndPoint()));
                //if one of the segments is entirely within the other is it sub-line adjacency
                if ((hasStart && hasEnd) || (hasOtherStart && hasOtherEnd))
                    return Optional.of(new Adjacency(AdjacencyType.SUBLINE));
                //if one or more of the segments is partially within the other it is partial adjacency TODO
                //one on one side and one on other side but not both is partial adjacency
                if ((hasStart || hasEnd) && (hasOtherStart || hasOtherEnd))
                    hasPartial = true;
            }
        }
        return hasPartial ? Optional.of(new Adjacency(AdjacencyType.PARTIAL)) : Optional.empty();
    }

    private boolean subsetEquals(LineConvexSubset subset1, LineConvexSubset subset2) {
        return (subset1.getStartPoint().equals(subset2.getStartPoint()) && subset1.getEndPoint().equals(subset2.getEndPoint()) ||
                subset1.getStartPoint().equals(subset2.getEndPoint()) && subset1.getEndPoint().equals(subset2.getStartPoint()));
    }

//TODO EQUALS
}
