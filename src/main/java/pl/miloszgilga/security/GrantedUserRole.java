/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jmpsl.security.user.IEnumerableUserRole;

@Getter
@RequiredArgsConstructor
public enum GrantedUserRole implements IEnumerableUserRole {
    STANDARD("STANDARD"),
    PREMIUM("PREMIUM"),
    ADMIN("ADMIN");

    private final String role;
}
