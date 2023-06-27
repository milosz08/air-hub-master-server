/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: IInGameWorkerParamRepository.java
 * Last modified: 6/23/23, 2:13 AM
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

package pl.miloszgilga.domain.in_game_worker_params;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.time.ZonedDateTime;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

@Repository
public interface IInGameWorkerParamRepository extends JpaRepository<InGameWorkerParamEntity, Long> {
    List<InGameWorkerParamEntity> findAllByUser_Id(Long userId);
    List<InGameWorkerParamEntity> findAllByIdInAndUser_Id(List<Long> workerIds, Long userId);
    boolean existsByIdAndUser_Id(Long workerId, Long userId);

    @Query(value = """
        from InGameWorkerParamEntity e where e.id not in
        (select c.worker.id from InGameCrewEntity c) and e.user.id = :uId and e.available = null
    """)
    List<InGameWorkerParamEntity> findAllExcInJoiningTable(@Param("uId") Long uId);

    @Modifying
    @Query(value = """
        update InGameWorkerParamEntity e set e.available = null
        where e.available is not null and e.available < :futureExpierd
    """)
    void revertAvailableWorkersState(@Param("futureExpierd") ZonedDateTime futureExpired);
}
