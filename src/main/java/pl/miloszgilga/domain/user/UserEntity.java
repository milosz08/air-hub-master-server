/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: UserEntity.java
 * Last modified: 17/05/2023, 15:20
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

package pl.miloszgilga.domain.user;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.*;

import java.util.Set;
import java.util.HashSet;
import java.io.Serial;
import java.io.Serializable;

import org.jmpsl.security.user.IAuthUserModel;
import org.jmpsl.security.user.IEnumerableUserRole;
import org.jmpsl.core.db.AbstractAuditableEntity;

import pl.miloszgilga.security.GrantedUserRole;

import pl.miloszgilga.domain.ota_token.OtaTokenEntity;
import pl.miloszgilga.domain.blacklist_jwt.BlacklistJwtEntity;
import pl.miloszgilga.domain.refresh_token.RefreshTokenEntity;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.CascadeType.ALL;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserEntity extends AbstractAuditableEntity implements Serializable, IAuthUserModel {
    @Serial private static final long serialVersionUID = 1L;

    @Column(name = "first_name")                            private String firstName;
    @Column(name = "last_name")                             private String lastName;
    @Column(name = "login")                                 private String login;
    @Column(name = "email_address")                         private String emailAddress;
    @Column(name = "password")                              private String password;
    @Column(name = "is_activated", insertable = false)      private Boolean isActivated;
    @Column(name = "role") @Enumerated(EnumType.STRING)     private GrantedUserRole role;

    @Builder.Default
    @OneToMany(cascade = ALL, fetch = LAZY, mappedBy = "user", orphanRemoval = true)
    private Set<OtaTokenEntity> otaTokens = new HashSet<>();

    @OneToOne(cascade = ALL, fetch = LAZY, mappedBy = "user", orphanRemoval = true)
    private RefreshTokenEntity refreshToken;

    @Builder.Default
    @OneToMany(cascade = ALL, fetch = LAZY, mappedBy = "user", orphanRemoval = true)
    private Set<BlacklistJwtEntity> blacklistJwts = new HashSet<>();

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public String getFirstName() {
        return firstName;
    }

    void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    void setLogin(String login) {
        this.login = login;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    void setEmailAddress(String emailAddress) {
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

    public GrantedUserRole getRole() {
        return role;
    }

    void setRole(GrantedUserRole role) {
        this.role = role;
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

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override public String getAuthUsername()                   { return login; }
    @Override public String getAuthPassword()                   { return password; }
    @Override public Set<IEnumerableUserRole> getAuthRoles()    { return Set.of(role); }
    @Override public boolean isAccountEnabled()                 { return isActivated; }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public String toString() {
        return "{" +
            "firstName=" + firstName +
            ", lastName=" + lastName +
            ", login=" + login +
            ", emailAddress=" + emailAddress +
            ", password=" + password +
            ", isActivated=" + isActivated +
            ", role=" + role +
            '}';
    }
}
