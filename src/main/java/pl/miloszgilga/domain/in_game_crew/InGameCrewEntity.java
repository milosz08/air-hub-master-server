/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: InGameCrewEntity.java
 * Last modified: 6/26/23, 6:33 AM
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

package pl.miloszgilga.domain.in_game_crew;

import jakarta.persistence.*;

import lombok.NoArgsConstructor;
import org.jmpsl.core.db.AbstractAuditableEntity;

import java.io.Serial;
import java.io.Serializable;

import pl.miloszgilga.domain.in_game_plane_params.InGamePlaneParamEntity;
import pl.miloszgilga.domain.in_game_worker_params.InGameWorkerParamEntity;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

@Entity
@NoArgsConstructor
@Table(name = "in_game_crew")
public class InGameCrewEntity extends AbstractAuditableEntity implements Serializable {
    @Serial private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plane_id", referencedColumnName = "id")
    private InGamePlaneParamEntity plane;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id", referencedColumnName = "id")
    private InGameWorkerParamEntity worker;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public InGameCrewEntity(InGamePlaneParamEntity plane, InGameWorkerParamEntity worker) {
        this.plane = plane;
        this.worker = worker;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public InGamePlaneParamEntity getPlane() {
        return plane;
    }

    public void setPlane(InGamePlaneParamEntity plane) {
        this.plane = plane;
    }

    public InGameWorkerParamEntity getWorker() {
        return worker;
    }

    public void setWorker(InGameWorkerParamEntity worker) {
        this.worker = worker;
    }
}
