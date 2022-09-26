package eu.wemakesoftware.jt.navsystem.controller;

import eu.wemakesoftware.jt.navsystem.controller.model.BaseStationReports;
import eu.wemakesoftware.jt.navsystem.controller.model.LocationResponse;
import eu.wemakesoftware.jt.navsystem.controller.model.NavSystemError;
import eu.wemakesoftware.jt.navsystem.location.Location;
import eu.wemakesoftware.jt.navsystem.location.LocationService;
import eu.wemakesoftware.jt.navsystem.report.ReportService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
public class NavSystemController {

    private final ReportService reportService;
    private final LocationService locationService;

    public NavSystemController(ReportService reportService, LocationService locationService) {
        this.reportService = reportService;
        this.locationService = locationService;
    }

    @Transactional
    @PostMapping("report")
    public void postReports(@RequestBody BaseStationReports baseStationReports){
        reportService.saveReports(baseStationReports.baseStationId, baseStationReports.reports);
    }

    @GetMapping("/location/{uuid}")
    public LocationResponse getMobileStationLocation(@PathVariable(name="uuid") String mobileStationUuid) {
        Location location;
        try {
            location = locationService.getLocation(mobileStationUuid);
        } catch (NavSystemError.TooFewReportsException | NavSystemError.UnknownIdException e) {
            return new LocationResponse(mobileStationUuid, e.error);
        }
        return new LocationResponse(location);
    }

}
