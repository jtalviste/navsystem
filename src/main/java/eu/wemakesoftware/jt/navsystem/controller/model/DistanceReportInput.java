package eu.wemakesoftware.jt.navsystem.controller.model;

import java.time.LocalDateTime;

public class DistanceReportInput {

    public final String mobileStationId;
    public final double distance;
    public final LocalDateTime timestamp;

    public DistanceReportInput(String mobileStationId, double distance, LocalDateTime timestamp) {
        this.mobileStationId = mobileStationId;
        this.distance = distance;
        this.timestamp = timestamp;
    }

    public String getMobileStationId() {
        return mobileStationId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
