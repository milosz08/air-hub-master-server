package pl.miloszgilga.ahms.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.miloszgilga.ahms.domain.user.UserEntity;
import pl.miloszgilga.ahms.domain.user.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class IdentifyUserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final UserEntity user = userRepository.findUserByLoginOrEmail(username).orElseThrow(() -> {
            log.error("Unable to load user with credentials data (login or email): {}", username);
            return new UsernameNotFoundException("Unable to load user in AuthUserDetailsService");
        });
        return new LoggedUser(user);
    }
}
