package eu.wemakesoftware.jt.navsystem.report;

import eu.wemakesoftware.jt.navsystem.basestation.BaseStation;
import eu.wemakesoftware.jt.navsystem.controller.model.DistanceReportInput;
import eu.wemakesoftware.jt.navsystem.jpa.BaseStationRepository;
import eu.wemakesoftware.jt.navsystem.jpa.DistanceReportRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

@Service
public class ReportService {

    private final DistanceReportRepository distanceReportRepository;
    private final BaseStationRepository baseStationRepository;

    public ReportService(DistanceReportRepository distanceReportRepository, BaseStationRepository baseStationRepository) {
        this.distanceReportRepository = distanceReportRepository;
        this.baseStationRepository = baseStationRepository;
    }

    public void saveReports(String baseStationUuid, List<DistanceReportInput> reports) {
        reports = new LinkedList<>(reports);
        //ignore if detection radius bigger
        BaseStation baseStation = baseStationRepository.getByUuid(baseStationUuid);
        reports.removeIf(report -> report.distance > baseStation.detectionRadiusInMeters);
        //remove duplicate reports
        Comparator<DistanceReportInput> comp = comparing(DistanceReportInput::getMobileStationId)
                .thenComparing(DistanceReportInput::getTimestamp);
        List<DistanceReportInput> duplicates = new LinkedList<>();
        reports.sort(comp);
        DistanceReportInput previous = null;
        for(DistanceReportInput report : reports){
            if(previous != null && previous.mobileStationId.equals(report.mobileStationId)){
                duplicates.add(report);
            }
            previous = report;
        }
        reports.removeAll(duplicates);
        //make sure don't have more than one report per MS<->BS pair
        //delete old reports with these BS,MS id pair
        for(DistanceReportInput report : reports){
            distanceReportRepository.deleteOldReports(baseStationUuid, report.mobileStationId);
        }
        distanceReportRepository.saveAll(
                reports.stream().map(reportInput ->
                        new DistanceReport(baseStationUuid, reportInput.mobileStationId, reportInput.distance,
                                reportInput.timestamp)).collect(Collectors.toList())
        );
        for(DistanceReportInput report : reports){
            distanceReportRepository.deleteAfterThreeReports(report.mobileStationId);
        }
    }

    public List<DistanceReport> getThreeDistanceReports(String mobileStationUuid) {
        return distanceReportRepository.findThreeDistanceReports(mobileStationUuid);
    }
}
