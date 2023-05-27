/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: IOtaTokenRepository.java
 * Last modified: 17/05/2023, 15:45
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

package pl.miloszgilga.domain.ota_token;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

@Repository
public interface IOtaTokenRepository extends JpaRepository<OtaTokenEntity, Long> {

    @Query(value = "select count(e.id) > 0 from OtaTokenEntity e where e.token = :token")
    boolean checkIfTokenAlreadyExist(@Param("token") String token);

    @Query(value = """
        from OtaTokenEntity e join fetch e.user u
        where e.token = :token and e.expiredAt > current_timestamp() and e.isUsed = false and e.type = :type
    """)
    Optional<OtaTokenEntity> findTokenByType(@Param("token") String token, @Param("type") OtaTokenType type);

    @Modifying
    @Query(value = "delete OtaTokenEntity e where e.isUsed = false and e.expiredAt < current_timestamp()")
    void deleteNonUsedOtaTokens();
}
