/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: InGamePlainParamEntity.java
 * Last modified: 6/23/23, 3:05 AM
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

package pl.miloszgilga.domain.in_game_plane_params;

import jakarta.persistence.*;

import java.util.Set;
import java.util.HashSet;
import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;

import pl.miloszgilga.domain.user.UserEntity;
import pl.miloszgilga.domain.plane.PlaneEntity;
import pl.miloszgilga.domain.plane_route.PlaneRouteEntity;
import pl.miloszgilga.domain.in_game_crew.InGameCrewEntity;

import org.jmpsl.core.db.AbstractAuditableEntity;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

@Entity
@Table(name = "in_game_plane_params")
public class InGamePlaneParamEntity extends AbstractAuditableEntity implements Serializable {
    @Serial private static final long serialVersionUID = 1L;

    @Column(name = "landing_geer")          private Integer landingGeer;
    @Column(name = "wings")                 private Integer wings;
    @Column(name = "engine")                private Integer engine;
    @Column(name = "upgrade")               private Integer upgrade;
    @Column(name = "available")             private ZonedDateTime available;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plane_id", referencedColumnName = "id")
    private PlaneEntity plane;

    @OneToMany(cascade = ALL, fetch = LAZY, mappedBy = "plane", orphanRemoval = true)
    private Set<PlaneRouteEntity> planeRouteEntities = new HashSet<>();

    @OneToMany(cascade = ALL, fetch = LAZY, mappedBy = "plane", orphanRemoval = true)
    private Set<InGameCrewEntity> crew = new HashSet<>();

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public InGamePlaneParamEntity() {
        landingGeer = 100;
        wings = 100;
        engine = 100;
        upgrade = 0;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Integer getLandingGeer() {
        return landingGeer;
    }

    public void setLandingGeer(Integer landingGeer) {
        this.landingGeer = landingGeer;
    }

    public Integer getWings() {
        return wings;
    }

    public void setWings(Integer wings) {
        this.wings = wings;
    }

    public Integer getEngine() {
        return engine;
    }

    public void setEngine(Integer engine) {
        this.engine = engine;
    }

    public Integer getUpgrade() {
        return upgrade;
    }

    void setUpgrade(Integer upgrade) {
        this.upgrade = upgrade;
    }

    public ZonedDateTime getAvailable() {
        return available;
    }

    public void setAvailable(ZonedDateTime avaialable) {
        this.available = avaialable;
    }

    UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public PlaneEntity getPlane() {
        return plane;
    }

    public void setPlane(PlaneEntity plane) {
        this.plane = plane;
    }

    public Set<PlaneRouteEntity> getPlaneRouteEntities() {
        return planeRouteEntities;
    }

    void setPlaneRouteEntities(Set<PlaneRouteEntity> planeRouteEntities) {
        this.planeRouteEntities = planeRouteEntities;
    }

    public Set<InGameCrewEntity> getCrew() {
        return crew;
    }

    void setCrew(Set<InGameCrewEntity> crew) {
        this.crew = crew;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void persistPlaneRouteEntity(PlaneRouteEntity planeRouteEntity) {
        planeRouteEntities.add(planeRouteEntity);
        planeRouteEntity.setPlane(this);
    }

    public void persistInGameCrewEntity(InGameCrewEntity inGameCrewEntity) {
        crew.add(inGameCrewEntity);
        inGameCrewEntity.setPlane(this);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
