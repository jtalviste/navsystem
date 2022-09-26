package eu.wemakesoftware.jt.navsystem.location;

public class Location {

    public final String mobileId;
    public final Double x;
    public final Double y;
    public final Double errorRadius;

    public Location(String mobileId, Double x, Double y, Double errorRadius) {
        this.mobileId = mobileId;
        this.x = x;
        this.y = y;
        this.errorRadius = errorRadius;
    }
}
