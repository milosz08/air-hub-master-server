/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.domain.in_game_worker_params;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.jmpsl.core.db.AbstractAuditableEntity;
import pl.miloszgilga.domain.in_game_crew.InGameCrewEntity;
import pl.miloszgilga.domain.user.UserEntity;
import pl.miloszgilga.domain.worker.WorkerEntity;
import pl.miloszgilga.domain.workers_shop.WorkerShopEntity;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor
@Table(name = "in_game_worker_params")
public class InGameWorkerParamEntity extends AbstractAuditableEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "skills")
    private Integer skills;

    @Column(name = "available")
    private LocalDateTime available;

    @Column(name = "experience")
    private Integer experience;

    @Column(name = "cooperation")
    private Integer cooperation;

    @Column(name = "rebelliousness")
    private Integer rebelliousness;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id", referencedColumnName = "id")
    private WorkerEntity worker;

    @OneToMany(cascade = ALL, fetch = LAZY, mappedBy = "worker", orphanRemoval = true)
    private Set<InGameCrewEntity> crew = new HashSet<>();

    public InGameWorkerParamEntity(WorkerShopEntity workerShopEntity) {
        skills = workerShopEntity.getSkills();
        experience = workerShopEntity.getExperience();
        cooperation = workerShopEntity.getCooperation();
        rebelliousness = workerShopEntity.getRebelliousness();
    }

    public Integer getSkills() {
        return skills;
    }

    void setSkills(Integer skills) {
        this.skills = skills;
    }

    public LocalDateTime getAvailable() {
        return available;
    }

    public void setAvailable(LocalDateTime available) {
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

    Set<InGameCrewEntity> getCrew() {
        return crew;
    }

    void setCrew(Set<InGameCrewEntity> crew) {
        this.crew = crew;
    }
    
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
