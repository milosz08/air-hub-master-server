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

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;

import pl.miloszgilga.domain.user.UserEntity;
import pl.miloszgilga.domain.plane.PlaneEntity;

import org.jmpsl.core.db.AbstractAuditableEntity;

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

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public InGamePlaneParamEntity() {
        landingGeer = 100;
        wings = 100;
        engine = 100;
        upgrade = 0;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    Integer getLandingGeer() {
        return landingGeer;
    }

    void setLandingGeer(Integer landingGeer) {
        this.landingGeer = landingGeer;
    }

    Integer getWings() {
        return wings;
    }

    void setWings(Integer wings) {
        this.wings = wings;
    }

    Integer getEngine() {
        return engine;
    }

    void setEngine(Integer engine) {
        this.engine = engine;
    }

    Integer getUpgrade() {
        return upgrade;
    }

    void setUpgrade(Integer upgrade) {
        this.upgrade = upgrade;
    }

    ZonedDateTime getAvailable() {
        return available;
    }

    void setAvailable(ZonedDateTime avaialable) {
        this.available = avaialable;
    }

    UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    PlaneEntity getPlane() {
        return plane;
    }

    public void setPlane(PlaneEntity plane) {
        this.plane = plane;
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
