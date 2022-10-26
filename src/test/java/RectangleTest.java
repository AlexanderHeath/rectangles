import org.example.Point;
import org.example.Rectangle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class RectangleTest {

    //TODO rotation test for all
    //TODO equals test for all
    //TODO null test for all

    @Test()
    public void testConstructInvalidRectangle() {
        IllegalArgumentException thrown1 = Assertions.assertThrows(IllegalArgumentException.class, () ->
                new Rectangle(new Point(1, 4), new Point(1, 5)));
        assertTrue(thrown1.getMessage().startsWith("Points cannot form a valid rectangle"));
        IllegalArgumentException thrown2 = Assertions.assertThrows(IllegalArgumentException.class, () ->
                new Rectangle(new Point(1, 4), new Point(2, 4)));
        assertTrue(thrown2.getMessage().startsWith("Points cannot form a valid rectangle"));
    }

    @Test
    public void testGetPoints() {
        Rectangle rec1 = new Rectangle(new Point(1, 4), new Point(4, 1));
        List<Point> expected = List.of(new Point(1,4), new Point(4,4), new Point(4,1), new Point(1,1));
        Point[] points = rec1.getPoints();
        assertEquals(expected.size(), points.length);
        for (Point point : points) {
            assertTrue(expected.contains(point));
        }
    }

    @Test
    public void testGetWidth() {
        Rectangle rec1 = new Rectangle(new Point(1, 4), new Point(6, 1));
        assertEquals(5, rec1.getWidth());
    }

    @Test
    public void testGetHeight() {
        Rectangle rec1 = new Rectangle(new Point(1, 4), new Point(6, 1));
        assertEquals(3, rec1.getHeight());
    }

    @Test
    public void testImmutable() {
        Rectangle rec1 = new Rectangle(new Point(1, 4), new Point(4, 1));
        List<Point> expected = List.of(new Point(1,4), new Point(4,4), new Point(4,1), new Point(1,1));
        Point[] points = rec1.getPoints();
        points[0] = new Point(1,2);
        points = rec1.getPoints();
        for (Point point : points) {
            assertTrue(expected.contains(point));
        }
    }

    @Test
    public void testHasAdjacencyLeftRightProper() {
        Rectangle rec1 = new Rectangle(new Point(1, 4), new Point(4, 1));
        Rectangle rec2 = new Rectangle(new Point(4, 4), new Point(6, 1));
        assertTrue(rec1.isAdjacentTo(rec2));
        //testing symmetric call
        assertTrue(rec2.isAdjacentTo(rec1));
    }

    @Test
    public void testHasAdjacencyLeftRightNone() {
        Rectangle rec1 = new Rectangle(new Point(1, 4), new Point(4, 1));
        Rectangle rec2 = new Rectangle(new Point(5, 4), new Point(6, 1));
        assertFalse(rec1.isAdjacentTo(rec2));
        //testing symmetric call
        assertFalse(rec2.isAdjacentTo(rec1));
    }

    @Test
    public void testHasAdjacencyLeftRightSubLine1() {
        //top sides same
        Rectangle rec1 = new Rectangle(new Point(1, 5), new Point(4, 1));
        Rectangle rec2 = new Rectangle(new Point(4, 5), new Point(6, 2));
        assertTrue(rec1.isAdjacentTo(rec2));
        //testing symmetric call
        assertTrue(rec2.isAdjacentTo(rec1));
    }

    @Test
    public void testHasAdjacencyLeftRightSubLine2() {
        //rec2 top & bottom between rec1
        Rectangle rec1 = new Rectangle(new Point(1, 5), new Point(4, 1));
        Rectangle rec2 = new Rectangle(new Point(4, 4), new Point(6, 2));
        assertTrue(rec1.isAdjacentTo(rec2));
        //testing symmetric call
        assertTrue(rec2.isAdjacentTo(rec1));
    }

    @Test
    public void testHasAdjacencyLeftRightSubLine3() {
        //bottom sides same
        Rectangle rec1 = new Rectangle(new Point(1, 5), new Point(4, 1));
        Rectangle rec2 = new Rectangle(new Point(4, 4), new Point(6, 1));
        assertTrue(rec1.isAdjacentTo(rec2));
        //testing symmetric call
        assertTrue(rec2.isAdjacentTo(rec1));
    }

    @Test
    public void testHasAdjacencyLeftRightPartial() {
        Rectangle rec1 = new Rectangle(new Point(3, 5), new Point(1, 2));
        Rectangle rec2 = new Rectangle(new Point(3, 7), new Point(5, 4));
        assertTrue(rec1.isAdjacentTo(rec2));
        //testing symmetric call
        assertTrue(rec2.isAdjacentTo(rec1));
    }

    @Test
    public void testHasAdjacencyTopBottomProper() {
        Rectangle rec1 = new Rectangle(new Point(1, 1), new Point(5, 4));
        Rectangle rec2 = new Rectangle(new Point(1, 6), new Point(5, 4));
        assertTrue(rec1.isAdjacentTo(rec2));
        //testing symmetric call
        assertTrue(rec2.isAdjacentTo(rec1));
    }

    @Test
    public void testHasAdjacencyTopBottomNone() {
        Rectangle rec1 = new Rectangle(new Point(1, 1), new Point(5, 4));
        Rectangle rec2 = new Rectangle(new Point(1, 6), new Point(5, 3));
        assertFalse(rec1.isAdjacentTo(rec2));
        //testing symmetric call
        assertFalse(rec2.isAdjacentTo(rec1));
    }

    @Test
    public void testHasAdjacencyTopBottomSubLine1() {
        //right sides same
        Rectangle rec1 = new Rectangle(new Point(1, 1), new Point(5, 4));
        Rectangle rec2 = new Rectangle(new Point(2, 6), new Point(5, 4));
        assertTrue(rec1.isAdjacentTo(rec2));
        //testing symmetric call
        assertTrue(rec2.isAdjacentTo(rec1));
    }

    @Test
    public void testHasAdjacencyTopBottomSubLine2() {
        //rec 2 left & right between rec1
        Rectangle rec1 = new Rectangle(new Point(1, 1), new Point(5, 4));
        Rectangle rec2 = new Rectangle(new Point(2, 6), new Point(4, 4));
        assertTrue(rec1.isAdjacentTo(rec2));
        //testing symmetric call
        assertTrue(rec2.isAdjacentTo(rec1));
    }

    @Test
    public void testHasAdjacencyTopBottomSubLine3() {
        //left sides same
        Rectangle rec1 = new Rectangle(new Point(1, 1), new Point(5, 4));
        Rectangle rec2 = new Rectangle(new Point(1, 6), new Point(4, 4));
        assertTrue(rec1.isAdjacentTo(rec2));
        //testing symmetric call
        assertTrue(rec2.isAdjacentTo(rec1));
    }

    @Test
    public void testHasAdjacencyTopBottomPartial() {
        Rectangle rec1 = new Rectangle(new Point(1, 3), new Point(4, 5));
        Rectangle rec2 = new Rectangle(new Point(2, 1), new Point(6, 3));
        assertTrue(rec1.isAdjacentTo(rec2));
        //testing symmetric call
        assertTrue(rec2.isAdjacentTo(rec1));
    }

    @Test
    public void testHasAdjacencyEndpoint() {
        Rectangle rec1 = new Rectangle(new Point(-1, 1), new Point(1, 2));
        Rectangle rec2 = new Rectangle(new Point(1, 2), new Point(3, 3));
        assertFalse(rec1.isAdjacentTo(rec2));
        //testing symmetric call
        assertFalse(rec2.isAdjacentTo(rec1));
    }

//    @Test
//    public void testGetAdjacencyPartial() {
//        Rectangle rec1 = new Rectangle(new Point(1, 6), new Point(4, 6), new Point(4, 1), new Point(1, 1));
//        Rectangle rec2 = new Rectangle(new Point(-2, 5), new Point(1, 5), new Point(1, 3), new Point(-2, 3));
//        Optional<Adjacency> result = rec1.getAdjacency(rec2);
//        assertTrue(result.isPresent());
//        assertEquals(AdjacencyType.SUBLINE, result.get().getType());
//        //testing symmetric call
//        Optional<Adjacency> result2 = rec2.getAdjacency(rec1);
//        assertTrue(result2.isPresent());
//        assertEquals(AdjacencyType.SUBLINE, result2.get().getType());
//    }

//    @Test
//    public void testEndpointAdjacency() {
//        Rectangle rec1 = new Rectangle(new Point(1, 3), new Point(5, 6), new Point(6, 4), new Point(3, 1));
//        Rectangle rec2 = new Rectangle(new Point(6, 6), new Point(9, 6), new Point(9, 2), new Point(6, 2));
//        Optional<Adjacency> result = rec1.getAdjacency(rec2);
//        assertTrue(result.isEmpty());
//        //testing symmetric call
//        Optional<Adjacency> result2 = rec2.getAdjacency(rec1);
//        assertTrue(result2.isEmpty());
//    }

    @Test
    public void testIntersection() {
        Rectangle rec1 = new Rectangle(new Point(2, 4), new Point(6, 2));
        Rectangle rec2 = new Rectangle(new Point(3, 3), new Point(5, 1));
        Set<Point> inter = rec1.getIntersection(rec2);
        assertEquals(2, inter.size());
        Point point1 = new Point(3, 2);
        Point point2 = new Point(5, 2);
        assertTrue(inter.contains(point1));
        assertTrue(inter.contains(point2));
        //testing symmetric call
        Set<Point> symInter = rec2.getIntersection(rec1);
        assertEquals(2, symInter.size());
        assertTrue(symInter.contains(point1));
        assertTrue(symInter.contains(point2));
    }

    @Test
    public void testIntersectionFourWay(){
        Rectangle rec1 = new Rectangle(new Point(1,6), new Point(3,0));
        Rectangle rec2 = new Rectangle(new Point(-4,3), new Point(5, 5));
        Set<Point> inter = rec1.getIntersection(rec2);
        assertEquals(4, inter.size());
        //testing symmetric call
        Set<Point> symInter = rec2.getIntersection(rec1);
        assertEquals(4, inter.size());
        List<Point> points = List.of(new Point(1,3),new Point(3,3),new Point(3,5),new Point(1,5));
        for (Point point : points) {
            assertTrue(inter.contains(point));
            assertTrue(symInter.contains(point));
        }
    }

    @Test
    public void testIntersectionCorner1(){
        //top left / bottom right
        Rectangle rec1 = new Rectangle(new Point(2, 4), new Point(6, 2));
        Rectangle rec2 = new Rectangle(new Point(1, 5), new Point(3, 3));
        Set<Point> inter = rec1.getIntersection(rec2);
        assertEquals(2, inter.size());
        Point point1 = new Point(2, 3);
        Point point2 = new Point(3, 4);
        assertTrue(inter.contains(point1));
        assertTrue(inter.contains(point2));
        //testing symmetric call
        Set<Point> symInter = rec2.getIntersection(rec1);
        assertEquals(2, symInter.size());
        assertTrue(symInter.contains(point1));
        assertTrue(symInter.contains(point2));
    }

    @Test
    public void testIntersectionCorner2(){
        //top right / bottom left
        Rectangle rec1 = new Rectangle(new Point(2, 4), new Point(6, 2));
        Rectangle rec2 = new Rectangle(new Point(5, 5), new Point(7, 3));
        Set<Point> inter = rec1.getIntersection(rec2);
        assertEquals(2, inter.size());
        Point point1 = new Point(6, 3);
        Point point2 = new Point(5, 4);
        assertTrue(inter.contains(point1));
        assertTrue(inter.contains(point2));
        //testing symmetric call
        Set<Point> symInter = rec2.getIntersection(rec1);
        assertEquals(2, symInter.size());
        assertTrue(symInter.contains(point1));
        assertTrue(symInter.contains(point2));
    }

    @Test
    public void testIntersectionSide1(){
        //bottom
        Rectangle rec1 = new Rectangle(new Point(2, 4), new Point(6, 2));
        Rectangle rec2 = new Rectangle(new Point(3, 3), new Point(5, 1));
        Set<Point> inter = rec1.getIntersection(rec2);
        assertEquals(2, inter.size());
        Point point1 = new Point(3, 2);
        Point point2 = new Point(5, 2);
        assertTrue(inter.contains(point1));
        assertTrue(inter.contains(point2));
        //testing symmetric call
        Set<Point> symInter = rec2.getIntersection(rec1);
        assertEquals(2, symInter.size());
        assertTrue(symInter.contains(point1));
        assertTrue(symInter.contains(point2));
    }
    @Test
    public void testIntersectionSide2(){
        //top
        Rectangle rec1 = new Rectangle(new Point(2, 4), new Point(6, 2));
        Rectangle rec2 = new Rectangle(new Point(3, 3), new Point(5, 5));
        Set<Point> inter = rec1.getIntersection(rec2);
        assertEquals(2, inter.size());
        Point point1 = new Point(3, 4);
        Point point2 = new Point(5, 4);
        assertTrue(inter.contains(point1));
        assertTrue(inter.contains(point2));
        //testing symmetric call
        Set<Point> symInter = rec2.getIntersection(rec1);
        assertEquals(2, symInter.size());
        assertTrue(symInter.contains(point1));
        assertTrue(symInter.contains(point2));
    }

    @Test
    public void testIntersectionSide3(){
        //left
        Rectangle rec1 = new Rectangle(new Point(2, 5), new Point(6, 1));
        Rectangle rec2 = new Rectangle(new Point(3, 3), new Point(1, 2));
        Set<Point> inter = rec1.getIntersection(rec2);
        assertEquals(2, inter.size());
        Point point1 = new Point(2, 2);
        Point point2 = new Point(2, 3);
        assertTrue(inter.contains(point1));
        assertTrue(inter.contains(point2));
        //testing symmetric call
        Set<Point> symInter = rec2.getIntersection(rec1);
        assertEquals(2, symInter.size());
        assertTrue(symInter.contains(point1));
        assertTrue(symInter.contains(point2));
    }

//    @Test
//    public void testIntersectionSide4(){
//        //right
//        Rectangle rec1 = new Rectangle(new Point(5, 5), new Point(6, 1));
//        Rectangle rec2 = new Rectangle(new Point(5, 2), new Point(7, 3));
//        Set<Point> inter = rec1.getIntersection(rec2);
//        assertFalse(inter.isEmpty());
//        assertEquals(2, inter.size());
//        Point point1 = new Point(6, 2);
//        Point point2 = new Point(6, 3);
//        assertTrue(inter.contains(point1));
//        assertTrue(inter.contains(point2));
//        //testing symmetric call
//        Set<Point> symInter = rec2.getIntersection(rec1);
//        assertFalse(symInter.isEmpty());
//        assertEquals(2, symInter.size());
//        assertTrue(symInter.contains(point1));
//        assertTrue(symInter.contains(point2));
//    }
    @Test
    public void testIntersectionSide4(){
        //right
        Rectangle rec1 = new Rectangle(new Point(2, 5), new Point(6, 1));
        Rectangle rec2 = new Rectangle(new Point(5, 2), new Point(7, 4));
        Set<Point> inter = rec1.getIntersection(rec2);
        assertEquals(2, inter.size());
        Point point1 = new Point(6, 4);
        Point point2 = new Point(6, 2);
        assertTrue(inter.contains(point1));
        assertTrue(inter.contains(point2));
        //testing symmetric call
        Set<Point> symInter = rec2.getIntersection(rec1);
        assertEquals(2, symInter.size());
        assertTrue(symInter.contains(point1));
        assertTrue(symInter.contains(point2));
    }

    @Test
    public void testIntersectionColinear1(){
        Rectangle rec1 = new Rectangle(new Point(2, 5), new Point(1, 1));
        Rectangle rec2 = new Rectangle(new Point(1,5), new Point(3, 4));
        Set<Point> inter = rec1.getIntersection(rec2);
        assertEquals(1, inter.size());
        Point point1 = new Point(2, 4);
        assertTrue(inter.contains(point1));
        //testing symmetric call
        Set<Point> symInter = rec2.getIntersection(rec1);
        assertEquals(1, symInter.size());
        assertTrue(symInter.contains(point1));
    }

    @Test
    public void testIntersectionColinear2(){
        Rectangle rec1 = new Rectangle(new Point(2, 5), new Point(1, 1));
        Rectangle rec2 = new Rectangle(new Point(1,3), new Point(3, 4));
        Set<Point> inter = rec1.getIntersection(rec2);
        assertEquals(2, inter.size());
        Point point1 = new Point(2, 4);
        Point point2 = new Point(2, 3);
        assertTrue(inter.contains(point1));
        assertTrue(inter.contains(point2));
        //testing symmetric call
        Set<Point> symInter = rec2.getIntersection(rec1);
        assertEquals(2, symInter.size());
        assertTrue(symInter.contains(point1));
        assertTrue(symInter.contains(point2));
    }

    @Test
    public void testIntersectionColinear3(){
        Rectangle rec1 = new Rectangle(new Point(2, 5), new Point(1, 1));
        Rectangle rec2 = new Rectangle(new Point(1,1), new Point(3, 2));
        Set<Point> inter = rec1.getIntersection(rec2);
        assertEquals(1, inter.size());
        Point point1 = new Point(2, 2);
        assertTrue(inter.contains(point1));
        //testing symmetric call
        Set<Point> symInter = rec2.getIntersection(rec1);
        assertEquals(1, symInter.size());
        assertTrue(symInter.contains(point1));
    }

    @Test
    public void testIntersectionNone() {
        Rectangle rec1 = new Rectangle(new Point(2, 4), new Point(6, 2));
        Rectangle rec2 = new Rectangle(new Point(3, 1), new Point(5, 0));
        Set<Point> inter = rec1.getIntersection(rec2);
        assertTrue(inter.isEmpty());
        //testing symmetric call
        Set<Point> symInter = rec2.getIntersection(rec1);
        assertTrue(symInter.isEmpty());
    }

    @Test
    public void testIntersectionEndpoint() {
        Rectangle rec1 = new Rectangle(new Point(1, 3), new Point(6, 4));
        Rectangle rec2 = new Rectangle(new Point(6, 6), new Point(9, 2));
        Set<Point> inter = rec1.getIntersection(rec2);
        assertTrue(inter.isEmpty());
        //testing symmetric call
        Set<Point> symInter = rec2.getIntersection(rec1);
        assertTrue(symInter.isEmpty());
    }

    //    @Test
//    public void testContainment() {
//        Rectangle rec1 = new Rectangle(new Point(1, 5), new Point(6, 5), new Point(6, 1), new Point(1, 1));
//        Rectangle rec2 = new Rectangle(new Point(2, 4), new Point(4, 4), new Point(4, 2), new Point(2, 2));
//        assertTrue(rec1.contains(rec2));
//        assertFalse(rec2.contains(rec1));
//    }
    @Test
    public void testContainment() {
        Rectangle rec1 = new Rectangle(new Point(1, 5), new Point(6, 1));
        Rectangle rec2 = new Rectangle(new Point(2, 4), new Point(4, 2));
        assertTrue(rec1.contains(rec2));
        assertFalse(rec2.contains(rec1));
    }

//    @Test
//    public void testRotatedContainment() {
//        Rectangle rec1 = new Rectangle(new Point(1, 5), new Point(5, 8), new Point(8, 4), new Point(4, 1));
//        Rectangle rec2 = new Rectangle(new Point(3, 5), new Point(4, 6), new Point(6, 4), new Point(5, 3));
//        assertTrue(rec1.contains(rec2));
//        assertFalse(rec2.contains(rec1));
//    }

    @Test
    public void testContainmentAdjacency() {
        Rectangle rec1 = new Rectangle(new Point(1, 5), new Point(5, 1));
        Rectangle rec2 = new Rectangle(new Point(3, 3), new Point(5, 1));
        assertTrue(rec1.contains(rec2));
        assertFalse(rec2.contains(rec1));

        System.out.println(rec1.isAdjacentTo(rec2));
        //TODO adjacency
    }

    //    @Test
//    public void testNoContainment() {
//        //no containment - intersection
//        Rectangle rec1 = new Rectangle(new Point(2, 4), new Point(6, 4), new Point(6, 2), new Point(2, 2));
//        Rectangle rec2 = new Rectangle(new Point(3, 3), new Point(5, 3), new Point(5, 1), new Point(3, 1));
//        assertFalse(rec1.contains(rec2));
//        assertFalse(rec2.contains(rec1));
//        //no containment
//        Rectangle rec3 = new Rectangle(new Point(2, 4), new Point(6, 4), new Point(6, 2), new Point(2, 2));
//        Rectangle rec4 = new Rectangle(new Point(3, 1), new Point(5, 1), new Point(5, 0), new Point(3, 0));
//        assertFalse(rec4.contains(rec3));
//        assertFalse(rec3.contains(rec4));
//    }
    @Test
    public void testNoContainment() {
        //no containment - intersection
        Rectangle rec1 = new Rectangle(new Point(2, 4), new Point(6, 2));
        Rectangle rec2 = new Rectangle(new Point(3, 3), new Point(5, 1));
        assertFalse(rec1.contains(rec2));
        assertFalse(rec2.contains(rec1));
        //no containment
        Rectangle rec3 = new Rectangle(new Point(2, 4), new Point(6, 2));
        Rectangle rec4 = new Rectangle(new Point(3, 1), new Point(5, 0));
        assertFalse(rec4.contains(rec3));
        assertFalse(rec3.contains(rec4));
    }

    @Test
    public void testEquals() {

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
