/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query(value = "from UserEntity e where e.login = :loginOrEmail or e.emailAddress = :loginOrEmail")
    Optional<UserEntity> findUserByLoginOrEmail(@Param("loginOrEmail") String loginOrEmail);

    @Query(value = "select count(e.id) > 0 from UserEntity e where e.login = :loginOrEmail or e.emailAddress = :loginOrEmail")
    boolean checkIfUserAlreadyExist(@Param("loginOrEmail") String loginOrEmail);

    @Query(value = "select count(e.id) > 0 from UserEntity e where e.login = :login and e.id <> :id")
    boolean checkIfUserWithSameLoginExist(@Param("login") String login, @Param("id") Long id);

    @Query(value = "select count(e.id) > 0 from UserEntity e where e.emailAddress = :emailAddress and e.id <> :id")
    boolean checkIfUserWithSameEmailExist(@Param("emailAddress") String emailAddress, @Param("id") Long id);

    @Modifying
    @Query(value = "delete UserEntity e where e.isActivated = false and e.createdAt < :futureExpierd")
    void deleteAllNotActivatedAccount(@Param("futureExpierd") ZonedDateTime futureExpired);

    @Modifying
    @Query(value = "update UserEntity e set e.isWorkersBlocked = false, e.isRoutesBlocked = false")
    void revalidateAllBlockedWorkersAndRoutes();
}
