/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.domain.ota_token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtaTokenRepository extends JpaRepository<OtaTokenEntity, Long> {
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
