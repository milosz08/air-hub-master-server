/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.domain.workers_shop;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.jmpsl.core.db.AbstractAuditableEntity;
import pl.miloszgilga.domain.user.UserEntity;
import pl.miloszgilga.domain.worker.WorkerEntity;

import java.io.Serial;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@Table(name = "workers_shop")
public class WorkerShopEntity extends AbstractAuditableEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "experience")
    private Integer experience;

    @Column(name = "cooperation")
    private Integer cooperation;

    @Column(name = "rebelliousness")
    private Integer rebelliousness;

    @Column(name = "skills")
    private Integer skills;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id", referencedColumnName = "id")
    private WorkerEntity worker;

    public WorkerShopEntity(int experience, int cooperation, int rebelliousness, int skills) {
        setAbilities(experience, cooperation, rebelliousness, skills);
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

    public Integer getSkills() {
        return skills;
    }

    public void setSkills(Integer skills) {
        this.skills = skills;
    }

    public WorkerEntity getWorker() {
        return worker;
    }

    public void setWorker(WorkerEntity worker) {
        this.worker = worker;
    }

    UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public void setAbilities(int experience, int cooperation, int rebelliousness, int skills) {
        this.experience = experience;
        this.cooperation = cooperation;
        this.rebelliousness = rebelliousness;
        this.skills = skills;
    }

    @Override
    public String toString() {
        return "{" +
            "experience=" + experience +
            ", cooperation=" + cooperation +
            ", rebelliousness=" + rebelliousness +
            ", skills=" + skills +
            '}';
    }
}


