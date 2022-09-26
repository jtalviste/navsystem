package eu.wemakesoftware.jt.navsystem.util;

import java.util.Objects;

public class Vector2D {

    public final double x,y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Vector2D{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public Vector2D() {
        this(0,0);
    }

    public double getDistance(Vector2D point2) {
        Vector2D delta = delta(point2);
        return delta.length();
    }

    public double length() {
        return Math.hypot(x,y);
    }

    private Vector2D delta(Vector2D point2) {
        return add(point2.scale(-1));
    }

    public Vector2D add(Vector2D point2) {
        return new Vector2D(x+ point2.x,y+ point2.y);
    }

    public Vector2D scale(double k) {
        return new Vector2D(x*k,y*k);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2D vector2D = (Vector2D) o;
        return Double.compare(vector2D.x, x) == 0 && Double.compare(vector2D.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public Vector2D sub(Vector2D p2) {
        return new Vector2D(x-p2.x,y-p2.y);
    }

    public Vector2D rot90() {
        return new Vector2D(-y,x);
    }

    public Vector2D avg(Vector2D other) {
        return add(other).scale(0.5);
    }
}
