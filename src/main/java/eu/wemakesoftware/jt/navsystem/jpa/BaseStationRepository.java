package eu.wemakesoftware.jt.navsystem.jpa;

import eu.wemakesoftware.jt.navsystem.basestation.BaseStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseStationRepository extends JpaRepository<BaseStation, Long> {

    @Query("SELECT baseStation FROM BaseStation baseStation WHERE baseStation.uuid = :baseStationUuid")
    BaseStation getByUuid(@Param("baseStationUuid") String baseStationUuid);
}
