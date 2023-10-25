/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.domain.blacklist_jwt;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.jmpsl.core.db.AbstractAuditableEntity;
import pl.miloszgilga.domain.user.UserEntity;

import java.io.Serializable;
import java.time.ZonedDateTime;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "blacklist_jwts")
public class BlacklistJwtEntity extends AbstractAuditableEntity implements Serializable {

    @Column(name = "jwt_token")
    private String jwtToken;

    @Column(name = "expired_at")
    private ZonedDateTime expiredAt;

    @ManyToOne(cascade = ALL, fetch = LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    String getJwtToken() {
        return jwtToken;
    }

    void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    ZonedDateTime getExpiredAt() {
        return expiredAt;
    }

    void setExpiredAt(ZonedDateTime expiredAt) {
        this.expiredAt = expiredAt;
    }

    UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "{" +
            "jwtToken=" + jwtToken +
            ", expiredAt=" + expiredAt +
            '}';
    }
}
