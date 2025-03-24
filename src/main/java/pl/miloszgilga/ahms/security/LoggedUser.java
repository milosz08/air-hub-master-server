package pl.miloszgilga.ahms.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.miloszgilga.ahms.domain.user.UserEntity;

import java.util.Collection;
import java.util.Collections;

public record LoggedUser(UserEntity userEntity) implements UserDetails {
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(userEntity.getRole().toAuthority());
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return userEntity.getLogin();
    }

    @Override
    public boolean isEnabled() {
        return userEntity.getIsActivated();
    }
}
