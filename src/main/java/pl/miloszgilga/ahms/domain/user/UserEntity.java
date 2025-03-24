package pl.miloszgilga.ahms.domain.user;

import jakarta.persistence.*;
import lombok.*;
import pl.miloszgilga.ahms.domain.AbstractAuditableEntity;
import pl.miloszgilga.ahms.domain.blacklist_jwt.BlacklistJwtEntity;
import pl.miloszgilga.ahms.domain.in_game_plane_params.InGamePlaneParamEntity;
import pl.miloszgilga.ahms.domain.in_game_worker_params.InGameWorkerParamEntity;
import pl.miloszgilga.ahms.domain.ota_token.OtaTokenEntity;
import pl.miloszgilga.ahms.domain.refresh_token.RefreshTokenEntity;
import pl.miloszgilga.ahms.domain.workers_shop.WorkerShopEntity;
import pl.miloszgilga.ahms.security.GrantedUserRole;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserEntity extends AbstractAuditableEntity implements Serializable {
    private String firstName;

    private String lastName;

    private String login;

    private String emailAddress;

    private String password;

    private Boolean isActivated;

    @Column(insertable = false)
    private Byte level;

    @Column(insertable = false)
    private Integer exp;

    @Column(insertable = false)
    private Long money;

    @Enumerated(EnumType.STRING)
    private GrantedUserRole role;

    @Column(insertable = false)
    private Boolean isWorkersBlocked;

    @Column(insertable = false)
    private Boolean isRoutesBlocked;

    @Column(insertable = false)
    private ZonedDateTime createdAt;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    private Set<OtaTokenEntity> otaTokens = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    private RefreshTokenEntity refreshToken;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    private Set<BlacklistJwtEntity> blacklistJwts = new HashSet<>();

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    private Set<InGameWorkerParamEntity> inGameWorkerParamEntities = new HashSet<>();

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    private Set<InGamePlaneParamEntity> inGamePlainParamEntities = new HashSet<>();

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    private Set<WorkerShopEntity> workerShopEntities = new HashSet<>();

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
