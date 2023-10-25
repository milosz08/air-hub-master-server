/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.domain.plane_route;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.jmpsl.core.db.AbstractAuditableEntity;
import pl.miloszgilga.domain.in_game_plane_params.InGamePlaneParamEntity;

import java.io.Serial;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@Table(name = "plane_routes")
public class PlaneRouteEntity extends AbstractAuditableEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "route_hours")
    private Integer routeHours;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "in_game_plane_id", referencedColumnName = "id")
    private InGamePlaneParamEntity plane;

    public Integer getRouteHours() {
        return routeHours;
    }

    public void setRouteHours(Integer routeHours) {
        this.routeHours = routeHours;
    }

    InGamePlaneParamEntity getPlane() {
        return plane;
    }

    public void setPlane(InGamePlaneParamEntity plane) {
        this.plane = plane;
    }

    @Override
    public String toString() {
        return "{" +
            "routeHours=" + routeHours +
            '}';
    }
}
