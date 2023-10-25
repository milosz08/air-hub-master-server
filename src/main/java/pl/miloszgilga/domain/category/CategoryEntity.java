/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.domain.category;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.jmpsl.core.db.AbstractAuditableEntity;
import pl.miloszgilga.domain.plane.PlaneEntity;
import pl.miloszgilga.domain.worker.WorkerEntity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categories")
public class CategoryEntity extends AbstractAuditableEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private CategoryType type;

    @Column(name = "level")
    private Integer level;

    @OneToMany(cascade = ALL, fetch = LAZY, mappedBy = "category", orphanRemoval = true)
    private Set<PlaneEntity> planes;

    @OneToMany(cascade = ALL, fetch = LAZY, mappedBy = "category", orphanRemoval = true)
    private Set<WorkerEntity> workers;

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

    public Integer getLevel() {
        return level;
    }

    void setLevel(Integer level) {
        this.level = level;
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

    public void addPlane(PlaneEntity planeEntity) {
        planes.add(planeEntity);
        planeEntity.setCategory(this);
    }

    public void addWorker(WorkerEntity workerEntity) {
        workers.add(workerEntity);
        workerEntity.setCategory(this);
    }

    @Override
    public String toString() {
        return "{" +
            "name=" + name +
            ", type=" + type +
            ", level=" + level +
            '}';
    }
}
