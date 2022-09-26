package eu.wemakesoftware.jt.navsystem.location;

import eu.wemakesoftware.jt.navsystem.basestation.BaseStation;
import eu.wemakesoftware.jt.navsystem.controller.model.NavSystemError;
import eu.wemakesoftware.jt.navsystem.jpa.BaseStationRepository;
import eu.wemakesoftware.jt.navsystem.jpa.DistanceReportRepository;
import eu.wemakesoftware.jt.navsystem.report.DistanceReport;
import eu.wemakesoftware.jt.navsystem.report.ReportService;
import eu.wemakesoftware.jt.navsystem.triangulation.TriangulatorService;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LocationServiceTest {

    @Test
    void givenThreeReportsAndBaseStations_whenGettingLocation_thenTriangulates() throws NavSystemError.UnknownIdException, NavSystemError.TooFewReportsException {
        DistanceReportRepository distanceReportRepository = mock(DistanceReportRepository.class);
        BaseStationRepository baseStationRepository = mock(BaseStationRepository.class);
        ReportService reportService = new ReportService(distanceReportRepository, baseStationRepository);
        LocationService locationService = new LocationService(reportService, new TriangulatorService(), baseStationRepository);
        when(distanceReportRepository.findThreeDistanceReports("1")).thenReturn(
                Arrays.asList(
                        new DistanceReport("1","1",8d, LocalDateTime.MAX),
                        new DistanceReport("2","1",8d, LocalDateTime.MAX),
                        new DistanceReport("3","1",8d, LocalDateTime.MAX)
                )
        );
        when(baseStationRepository.getByUuid("1")).thenReturn(new BaseStation("1",0d,0d,100D));
        when(baseStationRepository.getByUuid("2")).thenReturn(new BaseStation("2",5d,15d,100D));
        when(baseStationRepository.getByUuid("3")).thenReturn(new BaseStation("3",15d,5d,100D));

        Location location = locationService.getLocation("1");

        assertThat(location.mobileId).isEqualTo("1");
        assertThat(location.x).isCloseTo(6, Percentage.withPercentage(10));
        assertThat(location.y).isCloseTo(6, Percentage.withPercentage(10));
        assertThat(location.errorRadius).isCloseTo(2.5, Percentage.withPercentage(10));
    }

    @Test
    void givenZeroReports_whenGettingLocation_thenReportsUnknownId() {
        DistanceReportRepository distanceReportRepository = mock(DistanceReportRepository.class);
        BaseStationRepository baseStationRepository = mock(BaseStationRepository.class);
        ReportService reportService = new ReportService(distanceReportRepository, baseStationRepository);
        LocationService locationService = new LocationService(reportService, new TriangulatorService(), baseStationRepository);
        when(distanceReportRepository.findThreeDistanceReports("1")).thenReturn(
                Collections.emptyList()
        );
        Assertions.assertThrows(
                NavSystemError.UnknownIdException.class,
                () -> locationService.getLocation("1")
        );
    }

    @Test
    void givenTwoReports_whenGettingLocation_thenReportsTooFewReports() {
        DistanceReportRepository distanceReportRepository = mock(DistanceReportRepository.class);
        BaseStationRepository baseStationRepository = mock(BaseStationRepository.class);
        ReportService reportService = new ReportService(distanceReportRepository, baseStationRepository);
        LocationService locationService = new LocationService(reportService, new TriangulatorService(), baseStationRepository);
        when(distanceReportRepository.findThreeDistanceReports("1")).thenReturn(
                Arrays.asList(
                        new DistanceReport("1","1",8d, LocalDateTime.MAX),
                        new DistanceReport("2","1",8d, LocalDateTime.MAX)
                )
        );
        Assertions.assertThrows(
                NavSystemError.TooFewReportsException.class,
                () -> locationService.getLocation("1")
        );
    }

}
