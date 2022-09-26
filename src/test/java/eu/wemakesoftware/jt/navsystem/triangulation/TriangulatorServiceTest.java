package eu.wemakesoftware.jt.navsystem.triangulation;

import eu.wemakesoftware.jt.navsystem.util.Vector2D;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TriangulatorServiceTest {

    TriangulatorService triangulatorService = new TriangulatorService();

    @Test
    void checkClosestCircleIntersections(){
        Vector2D intersection = triangulatorService.getClosestCircleIntersection(
                new PointDistance(new Vector2D(), 1.4142),
                new PointDistance(new Vector2D(3,0), 2.236),
                new PointDistance(new Vector2D(0,3), 2.236)
        );
        assertEquals(1D, intersection.x, 0.001);
        assertEquals(1D, intersection.y, 0.001);
    }

    @Test
    void givenPointsAround_whenTriangulating_returnsCorrectResult(){
        Triangulation triangulation = triangulatorService.triangulate(
            new PointDistance(new Vector2D(), 1.4142),
            new PointDistance(new Vector2D(3,0), 2.236),
            new PointDistance(new Vector2D(0,3), 2.236)
        );
        assertEquals(1D, triangulation.positionEstimate.x, 0.001);
        assertEquals(1D, triangulation.positionEstimate.y, 0.001);
        assertEquals(0, triangulation.errorRadius, 0.001);
    }

    @Test
    void givenProximatePoints_whenTriangulating_returnsCorrectResult(){
        Triangulation triangulation = triangulatorService.triangulate(
                new PointDistance(new Vector2D(), 1.4142),
                new PointDistance(new Vector2D(1,0), 1),
                new PointDistance(new Vector2D(0,1), 1)
        );
        assertEquals(1D, triangulation.positionEstimate.x, 0.001);
        assertEquals(1D, triangulation.positionEstimate.y, 0.001);
        assertEquals(0, triangulation.errorRadius, 0.001);
    }

    @Test
    void givenDistancesWithErrors_whenTriangulating_returnsCorrectResult(){
        Triangulation triangulation = triangulatorService.triangulate(
                new PointDistance(new Vector2D(), 1.3),
                new PointDistance(new Vector2D(1,0), 1.1),
                new PointDistance(new Vector2D(0,1), 1.1)
        );
        assertEquals(1D, triangulation.positionEstimate.x, 0.1);
        assertEquals(1D, triangulation.positionEstimate.y, 0.1);
        assertEquals(0.2, triangulation.errorRadius, 0.05);
    }

}
