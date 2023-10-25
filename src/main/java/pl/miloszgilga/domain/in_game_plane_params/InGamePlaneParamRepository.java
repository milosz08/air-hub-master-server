/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.domain.in_game_plane_params;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface InGamePlaneParamRepository extends JpaRepository<InGamePlaneParamEntity, Long> {
    List<InGamePlaneParamEntity> findAllByUser_Id(Long userId);
    Optional<InGamePlaneParamEntity> findByIdAndUser_Id(Long planeId, Long userId);
    List<InGamePlaneParamEntity> findAllByUser_IdAndAvailableIsNotNull(Long userId);
    List<InGamePlaneParamEntity> findAllByUser_IdAndAvailableIsNull(Long userId);

    @Query(value = """
            from InGamePlaneParamEntity e where e.id not in
            (select c.plane.id from InGameCrewEntity c) and e.user.id = :uId and e.available = null
        """)
    List<InGamePlaneParamEntity> findAllExcInJoiningTable(@Param("uId") Long uId);

    @Query(value = """
            from InGamePlaneParamEntity e where e.id in
            (select c.plane.id from InGameCrewEntity c) and e.user.id = :uId and e.available = null
        """)
    List<InGamePlaneParamEntity> findAllInJoiningTable(@Param("uId") Long uId);

    @Query(value = """
            from InGamePlaneParamEntity e where e.id in
            (select c.plane.id from InGameCrewEntity c) and e.user.id = :uId and e.id = :pId
        """)
    Optional<InGamePlaneParamEntity> findByPlaneIdInJoiningTable(@Param("uId") Long uId, @Param("pId") Long pId);

    @Modifying
    @Query(value = """
            update InGamePlaneParamEntity e set e.available = null
            where e.available is not null and e.available < :futureExpired
        """)
    void revertAvailablePlanesState(@Param("futureExpired") LocalDateTime futureExpired);
}
