/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: BlacklistJwtEntity.java
 * Last modified: 17/05/2023, 19:50
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

package pl.miloszgilga.domain.blacklist_jwt;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

import org.jmpsl.core.db.AbstractAuditableEntity;
import pl.miloszgilga.domain.user.UserEntity;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "blacklist_jwts")
public class BlacklistJwtEntity extends AbstractAuditableEntity implements Serializable {

    @Column(name = "jwt_token")         private String jwtToken;
    @Column(name = "expired_at")        private ZonedDateTime expiredAt;

    @ManyToOne(cascade = ALL, fetch = LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public String toString() {
        return "{" +
            "jwtToken=" + jwtToken +
            ", expiredAt=" + expiredAt +
            '}';
    }
}
