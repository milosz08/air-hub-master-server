package pl.miloszgilga.ahms.domain.in_game_plane_params;

import jakarta.persistence.*;
import lombok.*;
import pl.miloszgilga.ahms.domain.AbstractAuditableEntity;
import pl.miloszgilga.ahms.domain.in_game_crew.InGameCrewEntity;
import pl.miloszgilga.ahms.domain.plane.PlaneEntity;
import pl.miloszgilga.ahms.domain.plane_route.PlaneRouteEntity;
import pl.miloszgilga.ahms.domain.user.UserEntity;

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
@Table(name = "in_game_plane_params")
public class InGamePlaneParamEntity extends AbstractAuditableEntity implements Serializable {
    private Integer landingGeer;

    private Integer wings;

    private Integer engine;

    private Integer upgrade;

    private LocalDateTime available;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private PlaneEntity plane;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "plane", orphanRemoval = true)
    private Set<PlaneRouteEntity> planeRouteEntities = new HashSet<>();

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "plane", orphanRemoval = true)
    private Set<InGameCrewEntity> crew = new HashSet<>();

    public void persistPlaneRouteEntity(PlaneRouteEntity planeRouteEntity) {
        planeRouteEntities.add(planeRouteEntity);
        planeRouteEntity.setPlane(this);
    }

    public void persistInGameCrewEntity(InGameCrewEntity inGameCrewEntity) {
        crew.add(inGameCrewEntity);
        inGameCrewEntity.setPlane(this);
    }

    @Override
    public String toString() {
        return "{" +
            "landingGeer=" + landingGeer +
            ", wings=" + wings +
            ", engine=" + engine +
            ", upgrade=" + upgrade +
            ", avaialable=" + available +
            '}';
    }
}
