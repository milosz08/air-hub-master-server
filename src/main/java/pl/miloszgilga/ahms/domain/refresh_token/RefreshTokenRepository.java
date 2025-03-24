package pl.miloszgilga.ahms.domain.refresh_token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {
    @Query(value = "from RefreshTokenEntity e join fetch e.user u where u.login = :login")
    Optional<RefreshTokenEntity> findRefreshTokenByUserLogin(@Param("login") String login);

    @Query(value = """
        from RefreshTokenEntity e join fetch e.user where e.token = :token and e.expiredAt > current_timestamp()
        """)
    Optional<RefreshTokenEntity> findRefreshTokenByToken(@Param("token") String token);
}
