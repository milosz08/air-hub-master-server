package pl.miloszgilga.ahms.domain.refresh_token;

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
@Table(name = "refresh_tokens")
public class RefreshTokenEntity extends AbstractAuditableEntity implements Serializable {
    private String token;

    private ZonedDateTime expiredAt;

    @JoinColumn
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UserEntity user;

    @Override
    public String toString() {
        return "{" +
            "token=" + token +
            ", expiredAt=" + expiredAt +
            '}';
    }
}
