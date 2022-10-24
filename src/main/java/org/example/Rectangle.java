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

    private static final double DEFAULT_TOLERANCE = 1.0e-10;

//    private final LineSegment[] segments;
//    private final LineSegment segment1;
//    private final LineSegment segment2;
//    private final LineSegment segment3;
//    private final LineSegment segment4;

//    private final Segment[] segments;
//    private final Region<Euclidean2D> region;

    //    public Rectangle(LineSegment segment1, LineSegment segment2, LineSegment segment3, LineSegment segment4) {
//        segments = new LineSegment[]{segment1, segment2, segment3, segment4};
////        this.segment1 = segment1;
////        this.segment2 = segment2;
////        this.segment3 = segment3;
////        this.segment4 = segment4;
//    }
//    private final Region<Vector2D> region;
    private final Parallelogram pGram;

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
//        Vector2D vec1 = new Vector2D(point1.getX(), point1.getY());
//        Vector2D vec2 = new Vector2D(point2.getX(), point2.getY());
//        Vector2D vec3 = new Vector2D(point3.getX(), point3.getY());
//        Vector2D vec4 = new Vector2D(point4.getX(), point4.getY());
//
//        Line line1 = new Line(vec1, vec2, DEFAULT_TOLERANCE);
//        Line line2 = new Line(vec2, vec3, DEFAULT_TOLERANCE);
//        Line line3 = new Line(vec3, vec4, DEFAULT_TOLERANCE);
//        Line line4 = new Line(vec4, vec1, DEFAULT_TOLERANCE);
//
//        segments = new Segment[]{
//                new Segment(vec1, vec2, line1),
//                new Segment(vec2, vec3, line2),
//                new Segment(vec3, vec4, line3),
//                new Segment(vec4, vec1, line4)
//        };
//        Set<SubHyperplane<Euclidean2D>> subLines = Set.of(
//                new SubLine(new Segment(vec1, vec2, line1)),
//                new SubLine(new Segment(vec2, vec3, line2)),
//                new SubLine(new Segment(vec3, vec4, line3)),
//                new SubLine(new Segment(vec4, vec1, line4))
//                );
//        region = new PolygonsSet(subLines, DEFAULT_TOLERANCE);
//        Segment segment1 = new Segment(vec1, vec2, line1);
//        Segment segment2 = new Segment(vec2, vec3, line2);
//        Segment segment3 = new Segment(vec3, vec4, line3);
//        Segment segment4 = new Segment(vec4, vec1, line4);


        Precision.DoubleEquivalence precision = Precision.doubleEquivalenceOfEpsilon(1e-6);
        Vector2D vec1 = Vector2D.of(point1.getX(), point1.getY());
        Vector2D vec2 = Vector2D.of(point2.getX(), point2.getY());
        Vector2D vec3 = Vector2D.of(point3.getX(), point3.getY());
        Vector2D vec4 = Vector2D.of(point4.getX(), point4.getY());

        pGram = Parallelogram.axisAligned(vec1, vec3, precision);

//        LinePath path = LinePath.fromVertexLoop(Arrays.asList(Vector2D.of(-4.0, 0.0), Vector2D.of(-2.0, -3.0), Vector2D.of(2.0, -3.0), Vector2D.of(4.0, 0.0), Vector2D.of(2.0, 3.0), Vector2D.of(-2.0, 3.0)), precision);
//        RegionBSPTree2D tree = RegionBSPTree2D.partitionedRegionBuilder().insertAxisAlignedGrid(path.getBounds(), 1, precision).insertBoundaries(path).build();
//        LinePath path = LinePath.builder(precision)
//                .append(vec1)
//                .append(vec2)
//                .append(vec3)
//                .append(vec4)
//                .build(true);
//        RegionBSPTree2D.partitionedRegionBuilder().insertAxisAlignedGrid(path.getBounds(),1,precision).insertBoundaries(path).build();
//        region = path.toTree();
    }

//    public Rectangle(LineSegment segment1, LineSegment segment2) {
//        this.segment1 = segment1;
//        this.segment2 = segment2;
//        this.segment3 = null;
//        this.segment4 = null;
//        throw new UnsupportedOperationException("Not yet implemented");
//    }

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

    //TODO end points included? if so remove dups?
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
//        for (Segment seg : segments) {
//            SubLine sub = new SubLine(seg);
//            for (Segment otherSeg : other.segments) {
//                Vector2D inter = sub.intersection(new SubLine(otherSeg), true);
//                if (inter != null) {
//                    points.add(new Point(inter.getX(), inter.getY()));
//                }
//            }
//        }
        return points;
    }

    //not symmetric
    public boolean contains(Rectangle other) {
        if (other == null || this.equals(other)) return false;
        for (Vector2D vector : other.pGram.getVertices()) {
            if (!pGram.contains(vector)) {
                return false;
            }
        }
        return true;
    }

    public Optional<Adjacency> getAdjacency(Rectangle other) {
        if (other == null) return Optional.empty();
        if (this.equals(other)) return Optional.of(new Adjacency(AdjacencyType.PROPER));
        List<LineConvexSubset> subs = pGram.getBoundaryPaths().get(0).getElements();
        List<LineConvexSubset> otherSubs = other.pGram.getBoundaryPaths().get(0).getElements();
        for (LineConvexSubset sub : subs) {
            for (LineConvexSubset otherSub : otherSubs) {
                //if the two segments are the same it is proper adjacency
                if (subsetEquals(sub, otherSub)) return Optional.of(new Adjacency(AdjacencyType.PROPER));
                boolean hasStart = otherSub.contains(sub.getStartPoint());
                boolean hasEnd = otherSub.contains(sub.getEndPoint());
                boolean hasOtherStart = sub.contains(otherSub.getStartPoint());
                boolean hasOtherEnd = sub.contains((otherSub.getEndPoint()));
                //if one of the segments is wholly within the other is it sub-line adjacency
                if ((hasStart && hasEnd) || (hasOtherStart && hasOtherEnd))
                    return Optional.of(new Adjacency(AdjacencyType.SUBLINE));
                //if one of the segments is partially within the other it is partial adjacency
                if ((hasStart || hasEnd) || (hasOtherStart || hasOtherEnd))
                    return Optional.of(new Adjacency(AdjacencyType.PARTIAL));
            }
        }
        return Optional.empty();
    }

    private boolean subsetEquals(LineConvexSubset subset1, LineConvexSubset subset2) {
        return (subset1.getStartPoint().equals(subset2.getStartPoint()) && subset1.getEndPoint().equals(subset2.getEndPoint()) ||
                subset1.getStartPoint().equals(subset2.getEndPoint()) && subset1.getEndPoint().equals(subset2.getStartPoint()));
    }

//TODO EQUALS
}
