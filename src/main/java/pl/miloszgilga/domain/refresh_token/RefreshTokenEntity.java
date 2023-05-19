/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: RefreshTokenEntity.java
 * Last modified: 17/05/2023, 19:44
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

package pl.miloszgilga.domain.refresh_token;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.*;

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
@Table(name = "refresh_tokens")
public class RefreshTokenEntity extends AbstractAuditableEntity implements Serializable {

    @Column(name = "token")         private String token;
    @Column(name = "expired_at")    private ZonedDateTime expiredAt;

    @OneToOne(cascade = ALL, fetch = LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ZonedDateTime getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(ZonedDateTime expiredAt) {
        this.expiredAt = expiredAt;
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
            '}';
    }
}
