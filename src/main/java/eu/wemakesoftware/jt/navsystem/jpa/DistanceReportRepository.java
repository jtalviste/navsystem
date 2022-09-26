package eu.wemakesoftware.jt.navsystem.jpa;

import eu.wemakesoftware.jt.navsystem.report.DistanceReport;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistanceReportRepository extends JpaRepository<DistanceReport, Long> {

    @Query("SELECT report FROM DistanceReport report WHERE report.mobileStationUuid = :mobileStationUuid")
    List<DistanceReport> findThreeDistanceReports(Pageable page, @Param("mobileStationUuid") String mobileStationUuid);

    default List<DistanceReport> findThreeDistanceReports(String mobileStationUuid){
        return findThreeDistanceReports(PageRequest.of(0,3, Sort.by(Sort.Direction.DESC, "timestamp")),
                mobileStationUuid);
    }

    default List<DistanceReport> findOverThreeDistanceReports(String mobileStationUuid){
        return findThreeDistanceReports(PageRequest.of(1,3, Sort.by(Sort.Direction.DESC, "timestamp")),
                mobileStationUuid);
    }

    default void deleteAfterThreeReports(String mobileStationUuid){
        List<DistanceReport> distanceReports;
        while(!(distanceReports = findOverThreeDistanceReports(mobileStationUuid)).isEmpty()){
            deleteAll(distanceReports);
        }
    }

    @Modifying
    @Query("DELETE FROM DistanceReport report WHERE report.baseStationUuid = :baseStationUuid and report.mobileStationUuid = :mobileStationUuid")
    void deleteOldReports(@Param("baseStationUuid") String baseStationUuid, @Param("mobileStationUuid") String mobileStationUuid);

}
