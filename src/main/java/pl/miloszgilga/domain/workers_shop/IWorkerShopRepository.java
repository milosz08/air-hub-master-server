/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: IWorkerShopRepository.java
 * Last modified: 6/23/23, 2:27 AM
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

package pl.miloszgilga.domain.workers_shop;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

@Repository
public interface IWorkerShopRepository extends JpaRepository<WorkerShopEntity, Long> {
    @Query(value = "from WorkerShopEntity e inner join e.user u join fetch e.worker where u.id = :uId")
    List<WorkerShopEntity> findByUseId(@Param("uId") Long uId);

    @Query(value = "from WorkerShopEntity e inner join e.user u inner join e.worker w where w.id = :wId and u.id = :uId")
    Optional<WorkerShopEntity> findByWorkerIdAndUserId(@Param("wId") Long wId, @Param("uId") Long uId);
}
