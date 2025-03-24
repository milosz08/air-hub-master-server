package pl.miloszgilga.ahms.domain.ota_token;

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
@Table(name = "ota_tokens")
public class OtaTokenEntity extends AbstractAuditableEntity implements Serializable {
    private String token;

    @Column(updatable = false)
    private ZonedDateTime expiredAt;

    @Enumerated(EnumType.STRING)
    private OtaTokenType type;

    private Boolean isUsed;

    @JoinColumn
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UserEntity user;

    @Override
    public String toString() {
        return "{" +
            "token=" + token +
            ", expiredAt=" + expiredAt +
            ", type=" + type +
            ", isUsed=" + isUsed +
            '}';
    }
}
