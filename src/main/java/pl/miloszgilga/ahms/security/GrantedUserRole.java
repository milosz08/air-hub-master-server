package pl.miloszgilga.ahms.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Getter
@RequiredArgsConstructor
public enum GrantedUserRole {
    USER("USER"),
    ;

    private final String role;

    public GrantedAuthority toAuthority() {
        return new SimpleGrantedAuthority(name());
    }
}
