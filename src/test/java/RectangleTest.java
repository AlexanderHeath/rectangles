import org.example.Adjacency;
import org.example.AdjacencyType;
import org.example.Point;
import org.example.Rectangle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class RectangleTest {

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
        List<Point> expected = List.of(new Point(1, 4), new Point(4, 4), new Point(4, 1), new Point(1, 1));
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
        List<Point> expected = List.of(new Point(1, 4), new Point(4, 4), new Point(4, 1), new Point(1, 1));
        Point[] points = rec1.getPoints();
        points[0] = new Point(1, 2);
        points = rec1.getPoints();
        for (Point point : points) {
            assertTrue(expected.contains(point));
        }
    }

    @Test
    public void testAdjacencyLeftRightProper() {
        Rectangle rec1 = new Rectangle(new Point(1, 4), new Point(4, 1));
        Rectangle rec2 = new Rectangle(new Point(4, 4), new Point(6, 1));
        assertTrue(rec1.isAdjacentTo(rec2));
        //testing symmetric call
        assertTrue(rec2.isAdjacentTo(rec1));
    }

    @Test
    public void testAdjacencyLeftRightNone() {
        Rectangle rec1 = new Rectangle(new Point(1, 4), new Point(4, 1));
        Rectangle rec2 = new Rectangle(new Point(5, 4), new Point(6, 1));
        assertFalse(rec1.isAdjacentTo(rec2));
        //testing symmetric call
        assertFalse(rec2.isAdjacentTo(rec1));
    }

    @Test
    public void testAdjacencyLeftRightSubLine1() {
        //top sides same
        Rectangle rec1 = new Rectangle(new Point(1, 5), new Point(4, 1));
        Rectangle rec2 = new Rectangle(new Point(4, 5), new Point(6, 2));
        assertTrue(rec1.isAdjacentTo(rec2));
        //testing symmetric call
        assertTrue(rec2.isAdjacentTo(rec1));
    }

    @Test
    public void testAdjacencyLeftRightSubLine2() {
        //rec2 top & bottom between rec1
        Rectangle rec1 = new Rectangle(new Point(1, 5), new Point(4, 1));
        Rectangle rec2 = new Rectangle(new Point(4, 4), new Point(6, 2));
        assertTrue(rec1.isAdjacentTo(rec2));
        //testing symmetric call
        assertTrue(rec2.isAdjacentTo(rec1));
    }

    @Test
    public void testAdjacencyLeftRightSubLine3() {
        //bottom sides same
        Rectangle rec1 = new Rectangle(new Point(1, 5), new Point(4, 1));
        Rectangle rec2 = new Rectangle(new Point(4, 4), new Point(6, 1));
        assertTrue(rec1.isAdjacentTo(rec2));
        //testing symmetric call
        assertTrue(rec2.isAdjacentTo(rec1));
    }

    @Test
    public void testAdjacencyLeftRightPartial() {
        Rectangle rec1 = new Rectangle(new Point(3, 5), new Point(1, 2));
        Rectangle rec2 = new Rectangle(new Point(3, 7), new Point(5, 4));
        assertTrue(rec1.isAdjacentTo(rec2));
        //testing symmetric call
        assertTrue(rec2.isAdjacentTo(rec1));
    }

    @Test
    public void testAdjacencyTopBottomProper() {
        Rectangle rec1 = new Rectangle(new Point(1, 1), new Point(5, 4));
        Rectangle rec2 = new Rectangle(new Point(1, 6), new Point(5, 4));
        assertTrue(rec1.isAdjacentTo(rec2));
        //testing symmetric call
        assertTrue(rec2.isAdjacentTo(rec1));
    }

    @Test
    public void testAdjacencyTopBottomNone() {
        Rectangle rec1 = new Rectangle(new Point(1, 1), new Point(5, 4));
        Rectangle rec2 = new Rectangle(new Point(1, 6), new Point(5, 3));
        assertFalse(rec1.isAdjacentTo(rec2));
        //testing symmetric call
        assertFalse(rec2.isAdjacentTo(rec1));
    }

    @Test
    public void testAdjacencyTopBottomSubLine1() {
        //right sides same
        Rectangle rec1 = new Rectangle(new Point(1, 1), new Point(5, 4));
        Rectangle rec2 = new Rectangle(new Point(2, 6), new Point(5, 4));
        assertTrue(rec1.isAdjacentTo(rec2));
        //testing symmetric call
        assertTrue(rec2.isAdjacentTo(rec1));
    }

    @Test
    public void testAdjacencyTopBottomSubLine2() {
        //rec 2 left & right between rec1
        Rectangle rec1 = new Rectangle(new Point(1, 1), new Point(5, 4));
        Rectangle rec2 = new Rectangle(new Point(2, 6), new Point(4, 4));
        assertTrue(rec1.isAdjacentTo(rec2));
        //testing symmetric call
        assertTrue(rec2.isAdjacentTo(rec1));
    }

    @Test
    public void testAdjacencyTopBottomSubLine3() {
        //left sides same
        Rectangle rec1 = new Rectangle(new Point(1, 1), new Point(5, 4));
        Rectangle rec2 = new Rectangle(new Point(1, 6), new Point(4, 4));
        assertTrue(rec1.isAdjacentTo(rec2));
        //testing symmetric call
        assertTrue(rec2.isAdjacentTo(rec1));
    }

    @Test
    public void testAdjacencyTopBottomPartial() {
        Rectangle rec1 = new Rectangle(new Point(1, 3), new Point(4, 5));
        Rectangle rec2 = new Rectangle(new Point(2, 1), new Point(6, 3));
        assertTrue(rec1.isAdjacentTo(rec2));
        //testing symmetric call
        assertTrue(rec2.isAdjacentTo(rec1));
    }

    @Test
    public void testAdjacencyContainment() {
        Rectangle rec1 = new Rectangle(new Point(1, 5), new Point(5, 1));
        Rectangle rec2 = new Rectangle(new Point(3, 3), new Point(5, 1));
        assertFalse(rec1.isAdjacentTo(rec2));
        //testing symmetric call
        assertFalse(rec2.isAdjacentTo(rec1));
    }

    @Test
    public void testAdjacencyEndpoint() {
        Rectangle rec1 = new Rectangle(new Point(-1, 1), new Point(1, 2));
        Rectangle rec2 = new Rectangle(new Point(1, 2), new Point(3, 3));
        assertFalse(rec1.isAdjacentTo(rec2));
        //testing symmetric call
        assertFalse(rec2.isAdjacentTo(rec1));
    }

    @Test
    public void testAdjacencyEquals() {
        Rectangle rec1 = new Rectangle(new Point(1, 4), new Point(4, 1));
        Rectangle rec2 = new Rectangle(new Point(1, 4), new Point(4, 1));
        assertFalse(rec1.isAdjacentTo(rec2));
        assertFalse(rec1.isAdjacentTo(rec1));
        //testing symmetric call
        assertFalse(rec2.isAdjacentTo(rec1));
    }

    @Test
    public void testAdjacencyNull() {
        Rectangle rec1 = new Rectangle(new Point(1, 4), new Point(4, 1));
        assertFalse(rec1.isAdjacentTo(null));
    }

    @Test
    public void testGetAdjacencyProper() {
        Rectangle rec1 = new Rectangle(new Point(1, 4), new Point(4, 1));
        Rectangle rec2 = new Rectangle(new Point(4, 4), new Point(6, 1));
        AdjacencyType expected = AdjacencyType.PROPER;
        Optional<Adjacency> result1 = rec1.getAdjacency(rec2);
        assertTrue(result1.isPresent());
        assertEquals(expected, result1.get().type());
        //testing symmetric call
        Optional<Adjacency> result2 = rec2.getAdjacency(rec1);
        assertTrue(result2.isPresent());
        assertEquals(expected, result2.get().type());
    }

    @Test
    public void testGetAdjacencyNone() {
        Rectangle rec1 = new Rectangle(new Point(1, 4), new Point(4, 1));
        Rectangle rec2 = new Rectangle(new Point(5, 4), new Point(6, 1));
        Optional<Adjacency> result1 = rec1.getAdjacency(rec2);
        assertTrue(result1.isEmpty());
        //testing symmetric call
        Optional<Adjacency> result2 = rec2.getAdjacency(rec1);
        assertTrue(result2.isEmpty());
    }

    @Test
    public void testGetAdjacencySubLine() {
        //top sides same
        Rectangle rec1 = new Rectangle(new Point(1, 5), new Point(4, 1));
        Rectangle rec2 = new Rectangle(new Point(4, 5), new Point(6, 2));
        AdjacencyType expected = AdjacencyType.SUBLINE;
        Optional<Adjacency> result1 = rec1.getAdjacency(rec2);
        assertTrue(result1.isPresent());
        assertEquals(expected, result1.get().type());
        //testing symmetric call
        Optional<Adjacency> result2 = rec2.getAdjacency(rec1);
        assertTrue(result2.isPresent());
        assertEquals(expected, result2.get().type());
    }

    @Test
    public void testGetAdjacencyPartial() {
        //top sides same
        Rectangle rec1 = new Rectangle(new Point(1, 5), new Point(4, 1));
        Rectangle rec2 = new Rectangle(new Point(4, 5), new Point(6, 2));
        AdjacencyType expected = AdjacencyType.SUBLINE;
        Optional<Adjacency> result1 = rec1.getAdjacency(rec2);
        assertTrue(result1.isPresent());
        assertEquals(expected, result1.get().type());
        //testing symmetric call
        Optional<Adjacency> result2 = rec2.getAdjacency(rec1);
        assertTrue(result2.isPresent());
        assertEquals(expected, result2.get().type());
    }

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
    public void testIntersectionFourWay() {
        Rectangle rec1 = new Rectangle(new Point(1, 6), new Point(3, 0));
        Rectangle rec2 = new Rectangle(new Point(-4, 3), new Point(5, 5));
        Set<Point> inter = rec1.getIntersection(rec2);
        assertEquals(4, inter.size());
        //testing symmetric call
        Set<Point> symInter = rec2.getIntersection(rec1);
        assertEquals(4, inter.size());
        List<Point> points = List.of(new Point(1, 3), new Point(3, 3), new Point(3, 5), new Point(1, 5));
        for (Point point : points) {
            assertTrue(inter.contains(point));
            assertTrue(symInter.contains(point));
        }
    }

    @Test
    public void testIntersectionCorner1() {
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
    public void testIntersectionCorner2() {
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
    public void testIntersectionSide1() {
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
    public void testIntersectionSide2() {
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
    public void testIntersectionSide3() {
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

    @Test
    public void testIntersectionSide4() {
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
    public void testIntersectionColinear1() {
        Rectangle rec1 = new Rectangle(new Point(2, 5), new Point(1, 1));
        Rectangle rec2 = new Rectangle(new Point(1, 5), new Point(3, 4));
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
    public void testIntersectionColinear2() {
        Rectangle rec1 = new Rectangle(new Point(2, 5), new Point(1, 1));
        Rectangle rec2 = new Rectangle(new Point(1, 3), new Point(3, 4));
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
    public void testIntersectionColinear3() {
        Rectangle rec1 = new Rectangle(new Point(2, 5), new Point(1, 1));
        Rectangle rec2 = new Rectangle(new Point(1, 1), new Point(3, 2));
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
    public void testIntersectionContainment() {
        Rectangle rec1 = new Rectangle(new Point(1, 5), new Point(6, 1));
        Rectangle rec2 = new Rectangle(new Point(2, 4), new Point(4, 2));
        assertTrue(rec1.getIntersection(rec2).isEmpty());
        assertTrue(rec2.getIntersection(rec1).isEmpty());
    }

    @Test
    public void testIntersectionAdjacent() {
        Rectangle rec1 = new Rectangle(new Point(1, 5), new Point(4, 1));
        Rectangle rec2 = new Rectangle(new Point(4, 5), new Point(6, 2));
        assertTrue(rec1.getIntersection(rec2).isEmpty());
        assertTrue(rec2.getIntersection(rec1).isEmpty());
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

    @Test
    public void testIntersectionEquals() {
        Rectangle rec1 = new Rectangle(new Point(1, 4), new Point(4, 1));
        Rectangle rec2 = new Rectangle(new Point(1, 4), new Point(4, 1));
        assertTrue(rec1.getIntersection(rec2).isEmpty());
        assertTrue(rec1.getIntersection(rec1).isEmpty());
        //testing symmetric call
        assertTrue(rec2.getIntersection(rec1).isEmpty());
    }

    @Test
    public void testIntersectionNull() {
        Rectangle rec1 = new Rectangle(new Point(1, 4), new Point(4, 1));
        assertTrue(rec1.getIntersection(null).isEmpty());
    }

    @Test
    public void testContainment() {
        Rectangle rec1 = new Rectangle(new Point(1, 5), new Point(6, 1));
        Rectangle rec2 = new Rectangle(new Point(2, 4), new Point(4, 2));
        assertTrue(rec1.contains(rec2));
        assertFalse(rec2.contains(rec1));
    }

    @Test
    public void testContainmentAdjacency() {
        Rectangle rec1 = new Rectangle(new Point(1, 5), new Point(5, 1));
        Rectangle rec2 = new Rectangle(new Point(3, 3), new Point(5, 1));
        assertTrue(rec1.contains(rec2));
        assertFalse(rec2.contains(rec1));
    }

    @Test
    public void testContainmentNone() {
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
    public void testContainmentEquals() {
        Rectangle rec1 = new Rectangle(new Point(1, 5), new Point(6, 1));
        Rectangle rec2 = new Rectangle(new Point(6, 1), new Point(1, 5));
        assertFalse(rec1.contains(rec2));
        assertFalse(rec2.contains(rec1));
    }

    @Test
    public void testContainmentNull() {
        Rectangle rec1 = new Rectangle(new Point(1, 5), new Point(6, 1));
        assertFalse(rec1.contains(null));
    }

    @Test
    public void testEquals() {
        Rectangle rec1 = new Rectangle(new Point(1, 4), new Point(4, 1));
        Rectangle rec2 = new Rectangle(new Point(4, 1), new Point(1, 4));
        assertEquals(rec1, rec2);
        assertEquals(rec1, rec1);
    }

}
