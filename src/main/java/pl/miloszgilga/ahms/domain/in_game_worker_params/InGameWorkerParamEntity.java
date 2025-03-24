package pl.miloszgilga.ahms.domain.in_game_worker_params;

import jakarta.persistence.*;
import lombok.*;
import pl.miloszgilga.ahms.domain.AbstractAuditableEntity;
import pl.miloszgilga.ahms.domain.in_game_crew.InGameCrewEntity;
import pl.miloszgilga.ahms.domain.user.UserEntity;
import pl.miloszgilga.ahms.domain.worker.WorkerEntity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "in_game_worker_params")
public class InGameWorkerParamEntity extends AbstractAuditableEntity implements Serializable {
    private Integer skills;

    private LocalDateTime available;

    private Integer experience;

    private Integer cooperation;

    private Integer rebelliousness;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private WorkerEntity worker;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "worker", orphanRemoval = true)
    private Set<InGameCrewEntity> crew = new HashSet<>();

    @Override
    public String toString() {
        return "{" +
            "skills=" + skills +
            ", available=" + available +
            ", experience=" + experience +
            ", cooperation=" + cooperation +
            ", rebelliousness=" + rebelliousness +
            '}';
    }
}
