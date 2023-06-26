/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: InGameWorkerParamEntity.java
 * Last modified: 6/23/23, 2:13 AM
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

package pl.miloszgilga.domain.in_game_worker_params;

import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;

import org.jmpsl.core.db.AbstractAuditableEntity;

import pl.miloszgilga.domain.user.UserEntity;
import pl.miloszgilga.domain.worker.WorkerEntity;
import pl.miloszgilga.domain.workers_shop.WorkerShopEntity;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

@Entity
@NoArgsConstructor
@Table(name = "in_game_worker_params")
public class InGameWorkerParamEntity extends AbstractAuditableEntity implements Serializable {
    @Serial private static final long serialVersionUID = 1L;

    @Column(name = "skills")                private Integer skills;
    @Column(name = "available")             private ZonedDateTime available;
    @Column(name = "experience")            private Integer experience;
    @Column(name = "cooperation")           private Integer cooperation;
    @Column(name = "rebelliousness")        private Integer rebelliousness;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id", referencedColumnName = "id")
    private WorkerEntity worker;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public InGameWorkerParamEntity(WorkerShopEntity workerShopEntity) {
        skills = 100;
        experience = workerShopEntity.getExperience();
        cooperation = workerShopEntity.getCooperation();
        rebelliousness = workerShopEntity.getRebelliousness();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Integer getSkills() {
        return skills;
    }

    void setSkills(Integer skills) {
        this.skills = skills;
    }

    public ZonedDateTime getAvailable() {
        return available;
    }

    public void setAvailable(ZonedDateTime available) {
        this.available = available;
    }

    public Integer getExperience() {
        return experience;
    }

    void setExperience(Integer experience) {
        this.experience = experience;
    }

    public Integer getCooperation() {
        return cooperation;
    }

    void setCooperation(Integer cooperation) {
        this.cooperation = cooperation;
    }

    public Integer getRebelliousness() {
        return rebelliousness;
    }

    void setRebelliousness(Integer rebelliousness) {
        this.rebelliousness = rebelliousness;
    }

    UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public WorkerEntity getWorker() {
        return worker;
    }

    public void setWorker(WorkerEntity worker) {
        this.worker = worker;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
