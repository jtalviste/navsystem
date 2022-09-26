package eu.wemakesoftware.jt.navsystem.location;

import eu.wemakesoftware.jt.navsystem.basestation.BaseStation;
import eu.wemakesoftware.jt.navsystem.controller.model.NavSystemError;
import eu.wemakesoftware.jt.navsystem.jpa.BaseStationRepository;
import eu.wemakesoftware.jt.navsystem.report.DistanceReport;
import eu.wemakesoftware.jt.navsystem.report.ReportService;
import eu.wemakesoftware.jt.navsystem.triangulation.PointDistance;
import eu.wemakesoftware.jt.navsystem.triangulation.Triangulation;
import eu.wemakesoftware.jt.navsystem.triangulation.TriangulatorService;
import eu.wemakesoftware.jt.navsystem.util.Vector2D;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class LocationService {

    private final ReportService reportService;
    private final TriangulatorService triangulatorService;
    private final BaseStationRepository baseStationRepository;

    public LocationService(ReportService reportService, TriangulatorService triangulatorService, BaseStationRepository baseStationRepository) {
        this.reportService = reportService;
        this.triangulatorService = triangulatorService;
        this.baseStationRepository = baseStationRepository;
    }

    public Location getLocation(String mobileStationUuid) throws NavSystemError.TooFewReportsException, NavSystemError.UnknownIdException {
        List<DistanceReport> distanceReports = reportService.getThreeDistanceReports(mobileStationUuid);
        if(distanceReports.size() == 0){
            throw new NavSystemError.UnknownIdException();
        }
        if(distanceReports.size() < 3){
            throw new NavSystemError.TooFewReportsException();
        }
        List<PointDistance> pointDistances = distanceReports.stream().map(
                distanceReport -> {
                    BaseStation baseStation = baseStationRepository.getByUuid(distanceReport.baseStationUuid);
                    return new PointDistance(new Vector2D(baseStation.x, baseStation.y), distanceReport.distance);
                }
        ).collect(toList());
        PointDistance p1 = pointDistances.get(0);
        PointDistance p2 = pointDistances.get(1);
        PointDistance p3 = pointDistances.get(2);
        Triangulation triangulation = triangulatorService.triangulate(p1, p2, p3);
        Vector2D positionEstimate = triangulation.positionEstimate;
        return new Location(mobileStationUuid, positionEstimate.x, positionEstimate.y,triangulation.errorRadius);
    }
}
