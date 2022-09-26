package eu.wemakesoftware.jt.navsystem.report;

import eu.wemakesoftware.jt.navsystem.basestation.BaseStation;
import eu.wemakesoftware.jt.navsystem.controller.model.DistanceReportInput;
import eu.wemakesoftware.jt.navsystem.jpa.BaseStationRepository;
import eu.wemakesoftware.jt.navsystem.jpa.DistanceReportRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.Mockito.*;

public class ReportServiceTest {

    @Test
    void checkReportSaving(){
        DistanceReportRepository distanceReportRepository = mock(DistanceReportRepository.class);
        BaseStationRepository baseStationRepository = mock(BaseStationRepository.class);
        ReportService reportService = new ReportService(distanceReportRepository, baseStationRepository);
        when(baseStationRepository.getByUuid("1")).thenReturn(
                new BaseStation("1", 0d,0d,1d)
        );
        reportService.saveReports("1", Arrays.asList(
                new DistanceReportInput("1",0, LocalDateTime.MAX),
                new DistanceReportInput("1",0,LocalDateTime.MAX),
                new DistanceReportInput("2",0,LocalDateTime.MAX),
                new DistanceReportInput("3",100,LocalDateTime.MAX)
        ));
        verify(distanceReportRepository).deleteOldReports("1", "1");
        verify(distanceReportRepository).deleteOldReports("1", "2");
        verify(distanceReportRepository, times(1)).saveAll(any());
        verify(distanceReportRepository, times(2)).deleteAfterThreeReports(any());
    }

    @Test
    void checkReportFinding(){
        DistanceReportRepository distanceReportRepository = mock(DistanceReportRepository.class);
        BaseStationRepository baseStationRepository = mock(BaseStationRepository.class);
        ReportService reportService = new ReportService(distanceReportRepository, baseStationRepository);
        reportService.getThreeDistanceReports("1");
        verify(distanceReportRepository, times(1)).findThreeDistanceReports("1");
    }

}
