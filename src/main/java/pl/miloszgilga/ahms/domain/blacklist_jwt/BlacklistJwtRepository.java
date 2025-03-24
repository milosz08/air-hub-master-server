package pl.miloszgilga.ahms.domain.blacklist_jwt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BlacklistJwtRepository extends JpaRepository<BlacklistJwtEntity, Long> {
    @Query(value = "select count(e.id) > 0 from BlacklistJwtEntity e where e.jwtToken = :jwt")
    boolean checkIfJwtIsOnBlacklist(@Param("jwt") String jwt);

    @Modifying
    @Query(value = "delete BlacklistJwtEntity e where e.expiredAt < current_timestamp()")
    void deleteAllExpiredJwts();
}
