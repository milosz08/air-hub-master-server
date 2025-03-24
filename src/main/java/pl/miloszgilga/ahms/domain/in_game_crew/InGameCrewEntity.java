package pl.miloszgilga.ahms.domain.in_game_crew;

import jakarta.persistence.*;
import lombok.*;
import pl.miloszgilga.ahms.domain.AbstractAuditableEntity;
import pl.miloszgilga.ahms.domain.in_game_plane_params.InGamePlaneParamEntity;
import pl.miloszgilga.ahms.domain.in_game_worker_params.InGameWorkerParamEntity;

import java.io.Serializable;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "in_game_crew")
public class InGameCrewEntity extends AbstractAuditableEntity implements Serializable {
    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private InGamePlaneParamEntity plane;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private InGameWorkerParamEntity worker;

    @Override
    public String toString() {
        return "{" +
            "plane=" + plane +
            ", worker=" + worker +
            '}';
    }
}
