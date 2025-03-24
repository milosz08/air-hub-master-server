package pl.miloszgilga.ahms.domain.blacklist_jwt;

import jakarta.persistence.*;
import lombok.*;
import pl.miloszgilga.ahms.domain.AbstractAuditableEntity;
import pl.miloszgilga.ahms.domain.user.UserEntity;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "blacklist_jwts")
public class BlacklistJwtEntity extends AbstractAuditableEntity implements Serializable {
    private String jwtToken;

    private ZonedDateTime expiredAt;

    @JoinColumn
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UserEntity user;

    @Override
    public String toString() {
        return "{" +
            "jwtToken=" + jwtToken +
            ", expiredAt=" + expiredAt +
            '}';
    }
}
