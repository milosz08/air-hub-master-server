/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: IInGamePlainParamRepository.java
 * Last modified: 6/23/23, 3:05 AM
 * Project name: air-hub-master-server
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the License at
 *
 *     <http://www.apache.org/license/LICENSE-2.0>
 *
 * Unless required by applicable law or agreed to in writing, software distributed under
 * the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the license.
 */

package pl.miloszgilga.domain.in_game_plane_params;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.time.ZonedDateTime;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

@Repository
public interface IInGamePlaneParamRepository extends JpaRepository<InGamePlaneParamEntity, Long> {
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
    void revertAvailablePlanesState(@Param("futureExpired") ZonedDateTime futureExpired);
}
