package pl.miloszgilga.ahms.domain.temp_stats;

import jakarta.persistence.*;
import lombok.*;
import pl.miloszgilga.ahms.domain.AbstractAuditableEntity;
import pl.miloszgilga.ahms.domain.in_game_plane_params.InGamePlaneParamEntity;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "temp_stats")
public class TempStatsEntity extends AbstractAuditableEntity implements Serializable {
    private Long selectedRoute;

    private LocalDateTime arrivalTime;

    private Byte upgradedLevel;

    private Integer addedExp;

    private Integer flightCosts;

    @JoinColumn
    @OneToOne(fetch = FetchType.LAZY)
    private InGamePlaneParamEntity inGamePlaneParam;

    @Override
    public String toString() {
        return "{" +
            "selectedRoute=" + selectedRoute +
            ", arrivalTime=" + arrivalTime +
            ", upgradedLevel=" + upgradedLevel +
            ", addedExp=" + addedExp +
            ", flightCosts=" + flightCosts +
            '}';
    }
}
