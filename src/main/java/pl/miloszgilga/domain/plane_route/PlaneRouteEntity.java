/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: PlaneRouteEntity.java
 * Last modified: 6/26/23, 5:17 AM
 * Project name: air-hub-master-server
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the License at
 *
 *     <http://www.apache.org/license/LICENSE-2.0>
 *
 * Unless required by applicable law or agreed to in writing, software distributed under
 * the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the license.
 */

package pl.miloszgilga.domain.plane_route;

import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

import org.jmpsl.core.db.AbstractAuditableEntity;

import pl.miloszgilga.domain.in_game_plane_params.InGamePlaneParamEntity;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

@Entity
@NoArgsConstructor
@Table(name = "plane_routes")
public class PlaneRouteEntity extends AbstractAuditableEntity implements Serializable {
    @Serial private static final long serialVersionUID = 1L;

    @Column(name = "route_hours")          private Integer routeHours;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "in_game_plane_id", referencedColumnName = "id")
    private InGamePlaneParamEntity plane;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public String toString() {
        return "{" +
            "routeHours=" + routeHours +
            '}';
    }
}
