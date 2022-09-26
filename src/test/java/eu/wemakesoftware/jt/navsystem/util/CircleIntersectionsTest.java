package eu.wemakesoftware.jt.navsystem.util;

import eu.wemakesoftware.jt.navsystem.triangulation.PointDistance;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CircleIntersectionsTest {

    @Test
    void givenNonIntersetingCircles_whenCalculating_thenReturnsMiddle(){
        PointDistance c1 = new PointDistance(new Vector2D(), 1);
        PointDistance c2 = new PointDistance(new Vector2D(3, 0), 1);
        List<Vector2D> intersections = new CircleIntersections(c1, c2).intersections;
        assertEquals(1, intersections.size());
        assertEquals(new Vector2D(1.5, 0), intersections.get(0));
    }

    @Test
    void givenIntersectingCircles_whenCalculating_thenReturnsTwoPoints(){
        PointDistance c1 = new PointDistance(new Vector2D(), 5);
        PointDistance c2 = new PointDistance(new Vector2D(6, 0), 5);
        List<Vector2D> intersections = new CircleIntersections(c1, c2).intersections;
        assertEquals(2, intersections.size());
        assertEquals(new Vector2D(3, 4), intersections.get(0));
        assertEquals(new Vector2D(3, -4), intersections.get(1));
    }

}
