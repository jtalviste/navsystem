package eu.wemakesoftware.jt.navsystem.basestation;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class BaseStation {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    public Integer id;

    @Column(nullable = false)
    public String uuid;

    @Column(nullable = false)
    public Double x;

    @Column(nullable = false)
    public Double y;

    @Column(nullable = false)
    public Double detectionRadiusInMeters;

    public BaseStation() {
    }

    public BaseStation(String uuid, Double x, Double y, Double detectionRadiusInMeters) {
        this.uuid = uuid;
        this.x = x;
        this.y = y;
        this.detectionRadiusInMeters = detectionRadiusInMeters;
    }
}