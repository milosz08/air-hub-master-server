package pl.miloszgilga.ahms.domain.temp_stats;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TempStatsRepository extends JpaRepository<TempStatsEntity, Long> {
    Optional<TempStatsEntity> findByInGamePlaneParam_IdAndInGamePlaneParam_User_Id(Long planeId, Long userId);

    void deleteByInGamePlaneParam_IdAndInGamePlaneParam_User_Id(Long planeId, Long userId);
}
