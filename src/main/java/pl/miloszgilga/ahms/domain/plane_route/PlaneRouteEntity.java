package pl.miloszgilga.ahms.domain.plane_route;

import jakarta.persistence.*;
import lombok.*;
import pl.miloszgilga.ahms.domain.AbstractAuditableEntity;
import pl.miloszgilga.ahms.domain.in_game_plane_params.InGamePlaneParamEntity;

import java.io.Serializable;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "plane_routes")
public class PlaneRouteEntity extends AbstractAuditableEntity implements Serializable {
    private Integer routeHours;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private InGamePlaneParamEntity plane;

    @Override
    public String toString() {
        return "{" +
            "routeHours=" + routeHours +
            '}';
    }
}
