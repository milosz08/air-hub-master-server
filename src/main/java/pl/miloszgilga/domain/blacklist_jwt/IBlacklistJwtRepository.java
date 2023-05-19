/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: IBlacklistJwtRepository.java
 * Last modified: 17/05/2023, 19:51
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

package pl.miloszgilga.domain.blacklist_jwt;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

@Repository
public interface IBlacklistJwtRepository extends JpaRepository<BlacklistJwtEntity, Long> {

    @Query(value = "select count(e.id) > 0 from BlacklistJwtEntity e where e.jwtToken = :jwt")
    boolean checkIfJwtIsOnBlacklist(@Param("jwt") String jwt);

    @Modifying
    @Query(value = "delete BlacklistJwtEntity e where e.expiredAt < current_timestamp()")
    void deleteAllExpiredJwts();
}
