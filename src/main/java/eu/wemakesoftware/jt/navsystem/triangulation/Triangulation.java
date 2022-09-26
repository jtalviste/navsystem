package eu.wemakesoftware.jt.navsystem.triangulation;

import eu.wemakesoftware.jt.navsystem.util.Vector2D;

public class Triangulation {

    public final Vector2D positionEstimate;
    public final double errorRadius;

    public Triangulation(Vector2D positionEstimate, double errorRadius) {
        this.positionEstimate = positionEstimate;
        this.errorRadius = errorRadius;
    }
}
