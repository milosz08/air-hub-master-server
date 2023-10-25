/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.domain.in_game_crew;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.jmpsl.core.db.AbstractAuditableEntity;
import pl.miloszgilga.domain.in_game_plane_params.InGamePlaneParamEntity;
import pl.miloszgilga.domain.in_game_worker_params.InGameWorkerParamEntity;

import java.io.Serial;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@Table(name = "in_game_crew")
public class InGameCrewEntity extends AbstractAuditableEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plane_id", referencedColumnName = "id")
    private InGamePlaneParamEntity plane;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id", referencedColumnName = "id")
    private InGameWorkerParamEntity worker;

    public InGameCrewEntity(InGamePlaneParamEntity plane, InGameWorkerParamEntity worker) {
        this.plane = plane;
        this.worker = worker;
    }

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

    @Override
    public String toString() {
        return "{" +
            "plane=" + plane +
            ", worker=" + worker +
            '}';
    }
}
