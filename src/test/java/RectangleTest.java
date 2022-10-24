import org.apache.commons.geometry.euclidean.twod.ConvexArea;
import org.apache.commons.geometry.euclidean.twod.Vector2D;
import org.apache.commons.geometry.euclidean.twod.path.LinePath;
import org.apache.commons.geometry.euclidean.twod.shape.Parallelogram;
import org.apache.commons.numbers.core.Precision;
import org.example.Adjacency;
import org.example.AdjacencyType;
import org.example.Point;
import org.example.Rectangle;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class RectangleTest {

    //TODO rotation test for all
    //TODO equals test for all
    //TODO null test for all

    @Test
    public void testGetAdjacencyProper(){
        Rectangle rec1 = new Rectangle(new Point(1,4), new Point(4,4), new Point(4,1), new Point(1,1));
        Rectangle rec2 = new Rectangle(new Point(4,4), new Point(6, 6), new Point(6, 1), new Point(4, 1));
        Optional<Adjacency> result = rec1.getAdjacency(rec2);
        assertTrue(result.isPresent());
        assertEquals(AdjacencyType.PROPER, result.get().getType());
    }

    @Test
    public void testIntersection() {
        Rectangle rec1 = new Rectangle(new Point(2, 4), new Point(6, 4), new Point(6, 2), new Point(2, 2));
        Rectangle rec2 = new Rectangle(new Point(3, 3), new Point(5, 3), new Point(5, 1), new Point(3, 1));
        Set<Point> inter = rec1.getIntersection(rec2);
        assertFalse(inter.isEmpty());
        assertEquals(2, inter.size());
        Point point1 = new Point(3, 2);
        Point point2 = new Point(5, 2);
        assertTrue(inter.contains(point1));
        assertTrue(inter.contains(point2));
        //testing symmetric call
        Set<Point> symInter = rec2.getIntersection(rec1);
        assertFalse(symInter.isEmpty());
        assertEquals(2, symInter.size());
        assertTrue(symInter.contains(point1));
        assertTrue(symInter.contains(point2));
    }

    @Test
    public void testNoIntersection() {
        Rectangle rec1 = new Rectangle(new Point(2, 4), new Point(6, 4), new Point(6, 2), new Point(2, 2));
        Rectangle rec2 = new Rectangle(new Point(3, 1), new Point(5, 1), new Point(5, 0), new Point(3, 0));
        Set<Point> inter = rec1.getIntersection(rec2);
        assertTrue(inter.isEmpty());
        //testing symmetric call
        Set<Point> symInter = rec2.getIntersection(rec1);
        assertTrue(symInter.isEmpty());
    }

    @Test
    public void testEndpointIntersection() {
        Rectangle rec1 = new Rectangle(new Point(1, 3), new Point(5, 6), new Point(6, 4), new Point(3, 1));
        Rectangle rec2 = new Rectangle(new Point(6, 6), new Point(9, 6), new Point(9, 2), new Point(6, 2));
        Set<Point> inter = rec1.getIntersection(rec2);
        assertFalse(inter.isEmpty());
        assertEquals(1, inter.size());
        Point point1 = new Point(6, 4);
        assertTrue(inter.contains(point1));
        //testing symmetric call
        Set<Point> symInter = rec2.getIntersection(rec1);
        assertFalse(symInter.isEmpty());
        assertEquals(1, symInter.size());
        assertTrue(symInter.contains(point1));
    }

    @Test
    public void testContainment() {
//        Precision.DoubleEquivalence precision = Precision.doubleEquivalenceOfEpsilon(1e-6);
//
////        LinePath path = LinePath.fromVertexLoop(Arrays.asList(Vector2D.of(-4.0, 0.0), Vector2D.of(-2.0, -3.0), Vector2D.of(2.0, -3.0), Vector2D.of(4.0, 0.0), Vector2D.of(2.0, 3.0), Vector2D.of(-2.0, 3.0)), precision);
////        RegionBSPTree2D tree = RegionBSPTree2D.partitionedRegionBuilder().insertAxisAlignedGrid(path.getBounds(), 1, precision).insertBoundaries(path).build();
//        Parallelogram parallelogram = Parallelogram.axisAligned(Vector2D.of(1,1), Vector2D.of(6,5),precision);
//        parallelogram.contains(Vector2D.of(2,2));
//
//        LinePath path = LinePath.builder(precision)
//                .append(Vector2D.of(1,1))
//                .append(Vector2D.of(1,5))
//                .append(Vector2D.of(6,5))
//                .append(Vector2D.of(6,1))
//                .build(true);
//        ConvexArea convexArea1 = ConvexArea.convexPolygonFromPath(parallelogram.getBoundaryPaths().get(0));
////        ConvexArea convexArea1 = ConvexArea.convexPolygonFromPath(path);
//        System.out.println(convexArea1.contains(Vector2D.of(2,2)));
//
////        Parallelogram parallelogram2 = Parallelogram.builder(precision).setScale(2).build();
////        System.out.println(parallelogram.contains(parallelogram2.getBoundaryPaths().get(0).getVertexSequence().get(0)));

        Rectangle rec1 = new Rectangle(new Point(1,5), new Point(6,5), new Point(6,1), new Point(1,1));
        Rectangle rec2 = new Rectangle(new Point(2,4), new Point(4,4), new Point(4,2), new Point(2,2));
        assertTrue(rec1.contains(rec2));
        assertFalse(rec2.contains(rec1));
    }

    @Test
    public void testRotatedContainment() {
        Rectangle rec1 = new Rectangle(new Point(1,5), new Point(5,8), new Point(8,4), new Point(4,1));
        Rectangle rec2 = new Rectangle(new Point(3,5), new Point(4,6), new Point(6,4), new Point(5,3));
        assertTrue(rec1.contains(rec2));
        assertFalse(rec2.contains(rec1));
    }

    @Test
    public void testContainmentAdjacency() {
        Rectangle rec1 = new Rectangle(new Point(1,5), new Point(5,5), new Point(5,1), new Point(1,1));
        Rectangle rec2 = new Rectangle(new Point(3,3), new Point(5,3), new Point(5,1), new Point(3,1));
        assertTrue(rec1.contains(rec2));
        assertFalse(rec2.contains(rec1));

        //TODO adjacency
    }

    @Test
    public void testNoContainment() {
        //no containment - intersection
        Rectangle rec1 = new Rectangle(new Point(2, 4), new Point(6, 4), new Point(6, 2), new Point(2, 2));
        Rectangle rec2 = new Rectangle(new Point(3, 3), new Point(5, 3), new Point(5, 1), new Point(3, 1));
        assertFalse(rec1.contains(rec2));
        assertFalse(rec2.contains(rec1));
        //no containment
        Rectangle rec3 = new Rectangle(new Point(2, 4), new Point(6, 4), new Point(6, 2), new Point(2, 2));
        Rectangle rec4 = new Rectangle(new Point(3, 1), new Point(5, 1), new Point(5, 0), new Point(3, 0));
        assertFalse(rec4.contains(rec3));
        assertFalse(rec3.contains(rec4));
    }


//    @Test
//    public void testIntersectionEndPoint(){
//        LineSegment seg1 = new LineSegment(new Point(1,1), new Point(1,4));
//        LineSegment seg2 = new LineSegment(new Point(4,1), new Point(4,4));
//        LineSegment seg3 = new LineSegment(new Point(1,4), new Point(4,4));
//        LineSegment seg4 = new LineSegment(new Point(1,1), new Point(4,1));
//        Rectangle rec1 = new Rectangle(seg1, seg2, seg3, seg4);
//        LineSegment seg5 = new LineSegment(new Point(), new Point());
//        LineSegment seg6 = new LineSegment(new Point(), new Point());
//        LineSegment seg7 = new LineSegment(new Point(), new Point());
//        LineSegment seg8 = new LineSegment(new Point(), new Point());
//        Rectangle rec2 = new Rectangle(seg5, seg6, seg7, seg8);
//        Optional<Adjacency> result = rec1.getAdjacency(rec2);
//        assertTrue(result.isPresent());
//        assertEquals(AdjacencyType.PROPER, result.get().getType());
//    }
}
