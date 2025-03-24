package pl.miloszgilga.ahms.security.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "application.jwt")
class JwtProperties {
    private String issuer;
    private String secret;
    private Integer accessExpiredSec;
    private Integer refreshExpiredSec;
}
