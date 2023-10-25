/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.domain.temp_stats;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.jmpsl.core.db.AbstractAuditableEntity;
import pl.miloszgilga.domain.in_game_plane_params.InGamePlaneParamEntity;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "temp_stats")
public class TempStatsEntity extends AbstractAuditableEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "selected_route")
    private Long selectedRoute;

    @Column(name = "arrival_time")
    private LocalDateTime arrivalTime;

    @Column(name = "upgraded_level")
    private Byte upgradedLevel;

    @Column(name = "added_exp")
    private Integer addedExp;

    @Column(name = "flight_costs")
    private Integer flightCosts;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "in_game_plane_id", referencedColumnName = "id")
    private InGamePlaneParamEntity plane;

    public Long getSelectedRoute() {
        return selectedRoute;
    }

    void setSelectedRoute(Long selectedRoute) {
        this.selectedRoute = selectedRoute;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    void setArrivalTime(LocalDateTime arrivalTime) {
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
