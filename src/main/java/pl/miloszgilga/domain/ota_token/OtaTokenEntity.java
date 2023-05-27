/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: OtaTokenEntity.java
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

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;

import pl.miloszgilga.domain.user.UserEntity;
import org.jmpsl.core.db.AbstractAuditableEntity;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.CascadeType.ALL;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ota_tokens")
public class OtaTokenEntity extends AbstractAuditableEntity implements Serializable {
    @Serial private static final long serialVersionUID = 1L;

    @Column(name = "token")                                                 private String token;
    @Column(name = "expired_at", updatable = false)                         private ZonedDateTime expiredAt;
    @Column(name = "type") @Enumerated(EnumType.STRING)                     private OtaTokenType type;
    @Column(name = "is_used")                                               private Boolean isUsed;

    @ManyToOne(cascade = ALL, fetch = LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
