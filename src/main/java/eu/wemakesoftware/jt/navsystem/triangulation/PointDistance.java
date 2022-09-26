package eu.wemakesoftware.jt.navsystem.triangulation;

import eu.wemakesoftware.jt.navsystem.util.Vector2D;

public class PointDistance {

    public final Vector2D position;
    public final double distance;

    public PointDistance(Vector2D position, double distance) {
        this.position = position;
        this.distance = distance;
    }
}
