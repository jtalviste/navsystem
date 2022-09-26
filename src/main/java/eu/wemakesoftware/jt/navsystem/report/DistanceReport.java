package eu.wemakesoftware.jt.navsystem.report;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class DistanceReport {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    public Integer id;

    @Column(nullable = false)
    public String baseStationUuid;

    @Column(nullable = false)
    public String mobileStationUuid;

    @Column(nullable = false)
    public Double distance;

    @Column(nullable = false)
    public LocalDateTime timestamp;

    public DistanceReport(){}

    public DistanceReport(String baseStationUuid, String mobileStationUuid, Double distance, LocalDateTime timestamp) {
        this.baseStationUuid = baseStationUuid;
        this.mobileStationUuid = mobileStationUuid;
        this.distance = distance;
        this.timestamp = timestamp;
    }
}