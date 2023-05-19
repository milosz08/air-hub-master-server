/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: JwtIssuer.java
 * Last modified: 18/05/2023, 14:54
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

package pl.miloszgilga.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;

import org.springframework.stereotype.Service;

import org.jmpsl.security.jwt.JwtService;

import pl.miloszgilga.domain.user.UserEntity;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

@Service
public class JwtIssuer {

    private final JwtService jwtService;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    JwtIssuer(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public String generateTokenForUser(UserEntity user) {
        final Claims customClaims = Jwts.claims();
        customClaims.put(JwtClaim.ID.getName(), user.getId());
        customClaims.put(JwtClaim.LOGIN.getName(), user.getLogin());
        customClaims.put(JwtClaim.ROLE.getName(), user.getRole().getRole());
        return jwtService.generateToken(user.getLogin(), customClaims);
    }
}
