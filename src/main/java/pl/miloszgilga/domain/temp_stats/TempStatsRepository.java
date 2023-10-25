/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.domain.temp_stats;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TempStatsRepository extends JpaRepository<TempStatsEntity, Long> {
    Optional<TempStatsEntity> findByPlane_IdAndPlane_User_Id(Long planeId, Long userId);
    void deleteByPlane_IdAndPlane_User_Id(Long planeId, Long userId);
}
