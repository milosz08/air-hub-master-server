/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: CategoryEntity.java
 * Last modified: 6/13/23, 11:55 PM
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

package pl.miloszgilga.domain.category;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.*;

import java.util.Set;
import java.io.Serial;
import java.io.Serializable;

import org.jmpsl.core.db.AbstractAuditableEntity;

import pl.miloszgilga.domain.plane.PlaneEntity;
import pl.miloszgilga.domain.worker.WorkerEntity;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categories")
public class CategoryEntity extends AbstractAuditableEntity implements Serializable {
    @Serial private static final long serialVersionUID = 1L;

    @Column(name = "name")                                  private String name;
    @Column(name = "type") @Enumerated(EnumType.STRING)     private CategoryType type;

    @OneToMany(cascade = ALL, fetch = LAZY, mappedBy = "category", orphanRemoval = true)
    private Set<PlaneEntity> planes;

    @OneToMany(cascade = ALL, fetch = LAZY, mappedBy = "category", orphanRemoval = true)
    private Set<WorkerEntity> workers;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    public CategoryType getType() {
        return type;
    }

    void setType(CategoryType type) {
        this.type = type;
    }

    Set<PlaneEntity> getPlanes() {
        return planes;
    }

    void setPlanes(Set<PlaneEntity> planes) {
        this.planes = planes;
    }

    Set<WorkerEntity> getWorkers() {
        return workers;
    }

    void setWorkers(Set<WorkerEntity> workers) {
        this.workers = workers;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void addPlane(PlaneEntity planeEntity) {
        planes.add(planeEntity);
        planeEntity.setCategory(this);
    }

    public void addWorker(WorkerEntity workerEntity) {
        workers.add(workerEntity);
        workerEntity.setCategory(this);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public String toString() {
        return "{" +
            "name=" + name +
            ", type=" + type +
            '}';
    }
}
