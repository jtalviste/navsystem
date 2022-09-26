package eu.wemakesoftware.jt.navsystem.controller.model;

import eu.wemakesoftware.jt.navsystem.location.Location;

public class LocationResponse {

    public final String mobileId;
    public final Double x;
    public final Double y;
    public final Double errorRadius;
    public final Integer errorCode;
    public final String errorDescription;

    public LocationResponse(String mobileId, Double x, Double y, Double errorRadius, Integer errorCode, String errorDescription) {
        this.mobileId = mobileId;
        this.x = x;
        this.y = y;
        this.errorRadius = errorRadius;
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }

    public LocationResponse(Location location){
        this(location.mobileId, location.x, location.y, location.errorRadius);
    }

    public LocationResponse(String mobileId, Double x, Double y, Double errorRadius){
        this(mobileId,x,y,errorRadius,null,null);
    }

    public LocationResponse(String mobileId,NavSystemError navSystemError) {
        this(mobileId,null,null,null,navSystemError.errorCode, navSystemError.description);
    }

}
