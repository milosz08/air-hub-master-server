/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: TempStatsEntity.java
 * Last modified: 6/26/23, 6:05 PM
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

package pl.miloszgilga.domain.temp_stats;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;

import org.jmpsl.core.db.AbstractAuditableEntity;

import pl.miloszgilga.domain.in_game_plane_params.InGamePlaneParamEntity;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "temp_stats")
public class TempStatsEntity extends AbstractAuditableEntity implements Serializable {
    @Serial private static final long serialVersionUID = 1L;

    @Column(name = "selected_route")    private Long selectedRoute;
    @Column(name = "arrival_time")      private ZonedDateTime arrivalTime;
    @Column(name = "upgraded_level")    private Byte upgradedLevel;
    @Column(name = "added_exp")         private Integer addedExp;
    @Column(name = "flight_costs")      private Integer flightCosts;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "in_game_plane_id", referencedColumnName = "id")
    private InGamePlaneParamEntity plane;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Long getSelectedRoute() {
        return selectedRoute;
    }

    void setSelectedRoute(Long selectedRoute) {
        this.selectedRoute = selectedRoute;
    }

    public ZonedDateTime getArrivalTime() {
        return arrivalTime;
    }

    void setArrivalTime(ZonedDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Byte getUpgradedLevel() {
        return upgradedLevel;
    }

    void setUpgradedLevel(Byte upgradedLevel) {
        this.upgradedLevel = upgradedLevel;
    }

    public Integer getAddedExp() {
        return addedExp;
    }

    void setAddedExp(Integer addedExp) {
        this.addedExp = addedExp;
    }

    public Integer getFlightCosts() {
        return flightCosts;
    }

    void setFlightCosts(Integer flightCosts) {
        this.flightCosts = flightCosts;
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
            "selectedRoute=" + selectedRoute +
            ", arrivalTime=" + arrivalTime +
            ", upgradedLevel=" + upgradedLevel +
            ", addedExp=" + addedExp +
            ", flightCosts=" + flightCosts +
            '}';
    }
}
