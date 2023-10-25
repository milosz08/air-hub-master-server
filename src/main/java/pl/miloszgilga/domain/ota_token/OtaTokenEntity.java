/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.domain.ota_token;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.jmpsl.core.db.AbstractAuditableEntity;
import pl.miloszgilga.domain.user.UserEntity;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ota_tokens")
public class OtaTokenEntity extends AbstractAuditableEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "token")
    private String token;

    @Column(name = "expired_at", updatable = false)
    private ZonedDateTime expiredAt;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private OtaTokenType type;

    @Column(name = "is_used")
    private Boolean isUsed;

    @ManyToOne(cascade = ALL, fetch = LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    String getToken() {
        return token;
    }

    void setToken(String token) {
        this.token = token;
    }

    ZonedDateTime getExpiredAt() {
        return expiredAt;
    }

    void setExpiredAt(ZonedDateTime expiredAt) {
        this.expiredAt = expiredAt;
    }

    OtaTokenType getType() {
        return type;
    }

    void setType(OtaTokenType type) {
        this.type = type;
    }

    Boolean getUsed() {
        return isUsed;
    }

    public void setUsed(Boolean used) {
        isUsed = used;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "{" +
            "token=" + token +
            ", expiredAt=" + expiredAt +
            ", type=" + type +
            ", isUsed=" + isUsed +
            '}';
    }
}
