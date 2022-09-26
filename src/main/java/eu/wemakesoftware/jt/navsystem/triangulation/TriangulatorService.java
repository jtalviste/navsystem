package eu.wemakesoftware.jt.navsystem.triangulation;

import eu.wemakesoftware.jt.navsystem.util.CircleIntersections;
import eu.wemakesoftware.jt.navsystem.util.Vector2D;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import static java.lang.Math.*;

@Service
public class TriangulatorService {

    public Triangulation triangulate(PointDistance input1, PointDistance input2, PointDistance input3){
        List<Vector2D> circleIntersections = Arrays.asList(
                getClosestCircleIntersection(input1, input2, input3),
                getClosestCircleIntersection(input1, input3, input2),
                getClosestCircleIntersection(input2, input3, input1)
        );
        Vector2D positionEstimate = estimatePosition(circleIntersections);
        double errorRadius = circleIntersections.stream()
                .mapToDouble(p -> p.getDistance(positionEstimate))
                .max()
                .orElse(Double.MAX_VALUE);
        return new Triangulation(positionEstimate, errorRadius);
    }

    private Vector2D estimatePosition(List<Vector2D> variants) {
        Vector2D positionEstimate = new Vector2D();

        for(Vector2D variant : variants){
            positionEstimate = positionEstimate.add(variant);
        }
        positionEstimate = positionEstimate.scale(1D / variants.size());

        return positionEstimate;
    }

    @NonNull
    Vector2D getClosestCircleIntersection(PointDistance center1, PointDistance center2, PointDistance proximate) {
        List<Vector2D> intersections = new CircleIntersections(center1, center2).intersections;
        Vector2D closest = intersections.get(0);
        for(Vector2D intersection : intersections){
            //return intersection with distance closest to estimate
            if(delta(intersection.getDistance(proximate.position), proximate.distance)
             < delta(closest.getDistance(proximate.position), proximate.distance)){
                closest = intersection;
            }
        }
        return closest;
    }

    private double delta(double a, double b) {
        return abs(a - b);
    }

}
