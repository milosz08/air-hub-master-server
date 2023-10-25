/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.domain.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.jmpsl.core.db.AbstractAuditableEntity;
import org.jmpsl.security.user.IAuthUserModel;
import org.jmpsl.security.user.IEnumerableUserRole;
import pl.miloszgilga.domain.blacklist_jwt.BlacklistJwtEntity;
import pl.miloszgilga.domain.in_game_plane_params.InGamePlaneParamEntity;
import pl.miloszgilga.domain.in_game_worker_params.InGameWorkerParamEntity;
import pl.miloszgilga.domain.ota_token.OtaTokenEntity;
import pl.miloszgilga.domain.refresh_token.RefreshTokenEntity;
import pl.miloszgilga.domain.workers_shop.WorkerShopEntity;
import pl.miloszgilga.security.GrantedUserRole;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserEntity extends AbstractAuditableEntity implements Serializable, IAuthUserModel {
    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "login")
    private String login;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "password")
    private String password;

    @Column(name = "is_activated")
    private Boolean isActivated;

    @Column(name = "level", insertable = false)
    private Byte level;

    @Column(name = "exp", insertable = false)
    private Integer exp;

    @Column(name = "money", insertable = false)
    private Long money;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private GrantedUserRole role;

    @Column(name = "is_workers_blocked", insertable = false)
    private Boolean isWorkersBlocked;

    @Column(name = "is_routes_blocked", insertable = false)
    private Boolean isRoutesBlocked;

    @Builder.Default
    @OneToMany(cascade = ALL, fetch = LAZY, mappedBy = "user", orphanRemoval = true)
    private Set<OtaTokenEntity> otaTokens = new HashSet<>();

    @OneToOne(cascade = ALL, fetch = LAZY, mappedBy = "user", orphanRemoval = true)
    private RefreshTokenEntity refreshToken;

    @Builder.Default
    @OneToMany(cascade = ALL, fetch = LAZY, mappedBy = "user", orphanRemoval = true)
    private Set<BlacklistJwtEntity> blacklistJwts = new HashSet<>();

    @OneToMany(cascade = ALL, fetch = LAZY, mappedBy = "user", orphanRemoval = true)
    private Set<InGameWorkerParamEntity> inGameWorkerParamEntities = new HashSet<>();

    @OneToMany(cascade = ALL, fetch = LAZY, mappedBy = "user", orphanRemoval = true)
    private Set<InGamePlaneParamEntity> inGamePlainParamEntities = new HashSet<>();

    @OneToMany(cascade = ALL, fetch = LAZY, mappedBy = "user", orphanRemoval = true)
    private Set<WorkerShopEntity> workerShopEntities = new HashSet<>();

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    Set<OtaTokenEntity> getOtaTokens() {
        return otaTokens;
    }

    void setOtaTokens(Set<OtaTokenEntity> otaTokens) {
        this.otaTokens = otaTokens;
    }

    public Boolean getActivated() {
        return isActivated;
    }

    public void setActivated(Boolean activated) {
        isActivated = activated;
    }

    public Byte getLevel() {
        return level;
    }

    public void setLevel(Byte level) {
        this.level = level;
    }

    public Integer getExp() {
        return exp;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
    }

    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        this.money = money;
    }

    public GrantedUserRole getRole() {
        return role;
    }

    void setRole(GrantedUserRole role) {
        this.role = role;
    }

    public Boolean getWorkersBlocked() {
        return isWorkersBlocked;
    }

    public void setWorkersBlocked(Boolean workersBlocked) {
        isWorkersBlocked = workersBlocked;
    }

    public Boolean getRoutesBlocked() {
        return isRoutesBlocked;
    }

    public void setRoutesBlocked(Boolean routesBlocked) {
        isRoutesBlocked = routesBlocked;
    }

    RefreshTokenEntity getRefreshToken() {
        return refreshToken;
    }

    void setRefreshToken(RefreshTokenEntity refreshToken) {
        this.refreshToken = refreshToken;
    }

    Set<BlacklistJwtEntity> getBlacklistJwts() {
        return blacklistJwts;
    }

    void setBlacklistJwts(Set<BlacklistJwtEntity> blacklistJwts) {
        this.blacklistJwts = blacklistJwts;
    }

    Set<InGameWorkerParamEntity> getInGameWorkerParamEntities() {
        return inGameWorkerParamEntities;
    }

    void setInGameWorkerParamEntities(Set<InGameWorkerParamEntity> inGameWorkerParamEntities) {
        this.inGameWorkerParamEntities = inGameWorkerParamEntities;
    }

    Set<InGamePlaneParamEntity> getInGamePlainParamEntities() {
        return inGamePlainParamEntities;
    }

    void setInGamePlainParamEntities(Set<InGamePlaneParamEntity> inGamePlainParamEntities) {
        this.inGamePlainParamEntities = inGamePlainParamEntities;
    }

    Set<WorkerShopEntity> getWorkerShopEntities() {
        return workerShopEntities;
    }

    void setWorkerShopEntities(Set<WorkerShopEntity> workerShopEntities) {
        this.workerShopEntities = workerShopEntities;
    }

    public void persistOtaToken(OtaTokenEntity otaTokenEntity) {
        otaTokens.add(otaTokenEntity);
        otaTokenEntity.setUser(this);
    }

    public void persistRefreshToken(RefreshTokenEntity refreshTokenEntity) {
        refreshToken = refreshTokenEntity;
        refreshTokenEntity.setUser(this);
    }

    public void persistBlacklistJwt(BlacklistJwtEntity blacklistJwtEntity) {
        blacklistJwts.add(blacklistJwtEntity);
        blacklistJwtEntity.setUser(this);
    }

    public void persistInGameWorkerParamEntity(InGameWorkerParamEntity inGameWorkerParamEntity) {
        inGameWorkerParamEntities.add(inGameWorkerParamEntity);
        inGameWorkerParamEntity.setUser(this);
    }

    public void persistInGamePlainParamEntity(InGamePlaneParamEntity inGamePlaneParamEntity) {
        inGamePlainParamEntities.add(inGamePlaneParamEntity);
        inGamePlaneParamEntity.setUser(this);
    }

    public void persistWorkerShopEntity(WorkerShopEntity workerShopEntity) {
        workerShopEntities.add(workerShopEntity);
        workerShopEntity.setUser(this);
    }

    @Override
    public String getAuthUsername() {
        return login;
    }

    @Override
    public String getAuthPassword() {
        return password;
    }

    @Override
    public Set<IEnumerableUserRole> getAuthRoles() {
        return Set.of(role);
    }

    @Override
    public boolean isAccountEnabled() {
        return isActivated;
    }
    
    @Override
    public String toString() {
        return "{" +
            "firstName=" + firstName +
            ", lastName=" + lastName +
            ", login=" + login +
            ", emailAddress=" + emailAddress +
            ", password=" + password +
            ", isActivated=" + isActivated +
            ", level=" + level +
            ", exp=" + exp +
            ", money=" + money +
            ", role=" + role +
            ", isWorkersBlocked=" + isWorkersBlocked +
            ", isRoutesBlocked=" + isRoutesBlocked +
            '}';
    }
}
