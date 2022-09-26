package eu.wemakesoftware.jt.navsystem.util;

import eu.wemakesoftware.jt.navsystem.triangulation.PointDistance;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

//http://paulbourke.net/geometry/circlesphere/
public class CircleIntersections {

    public final List<Vector2D> intersections;

    public CircleIntersections(PointDistance c1, PointDistance c2) {
        Vector2D deltaCenters = c2.position.sub(c1.position);
        double distanceOfCenters = deltaCenters.length();

        if(distanceOfCenters > c1.distance + c2.distance){
            this.intersections = Collections.singletonList(c1.position.avg(c2.position));
            return;
        }

        Vector2D deltaUnit = deltaCenters.scale(1 / distanceOfCenters);
        double distanceToRadicalLine = (square(distanceOfCenters) + square(c1.distance) - square(c2.distance)) / (2 * distanceOfCenters);
        Vector2D radicalPoint = c1.position.add(deltaUnit.scale(distanceToRadicalLine));
        Vector2D radicalDirectionVector = deltaUnit.rot90();

        double centerToIntersectionPoints = sqrt(square(c1.distance) - square(distanceToRadicalLine));
        Vector2D intersectionPoint1 = radicalPoint.add(radicalDirectionVector.scale(centerToIntersectionPoints));
        Vector2D intersectionPoint2 = radicalPoint.add(radicalDirectionVector.scale(-centerToIntersectionPoints));

        this.intersections = Arrays.asList(intersectionPoint1, intersectionPoint2);
    }

    private double square(double a) {
        return pow(a, 2);
    }

}
