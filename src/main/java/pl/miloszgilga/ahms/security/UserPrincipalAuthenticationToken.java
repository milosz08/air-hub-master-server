package pl.miloszgilga.ahms.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

public class UserPrincipalAuthenticationToken extends AbstractAuthenticationToken {
    private final UserDetails principal;

    public UserPrincipalAuthenticationToken(HttpServletRequest req, UserDetails principal) {
        super(principal.getAuthorities());
        this.principal = principal;
        setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}
