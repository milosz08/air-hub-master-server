/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.domain.in_game_plane_params;

import jakarta.persistence.*;
import org.jmpsl.core.db.AbstractAuditableEntity;
import pl.miloszgilga.domain.in_game_crew.InGameCrewEntity;
import pl.miloszgilga.domain.plane.PlaneEntity;
import pl.miloszgilga.domain.plane_route.PlaneRouteEntity;
import pl.miloszgilga.domain.user.UserEntity;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "in_game_plane_params")
public class InGamePlaneParamEntity extends AbstractAuditableEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "landing_geer")
    private Integer landingGeer;

    @Column(name = "wings")
    private Integer wings;

    @Column(name = "engine")
    private Integer engine;

    @Column(name = "upgrade")
    private Integer upgrade;

    @Column(name = "available")
    private LocalDateTime available;

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

    public InGamePlaneParamEntity() {
        landingGeer = 100;
        wings = 100;
        engine = 100;
        upgrade = 0;
    }

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

    public LocalDateTime getAvailable() {
        return available;
    }

    public void setAvailable(LocalDateTime avaialable) {
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

    public void persistPlaneRouteEntity(PlaneRouteEntity planeRouteEntity) {
        planeRouteEntities.add(planeRouteEntity);
        planeRouteEntity.setPlane(this);
    }

    public void persistInGameCrewEntity(InGameCrewEntity inGameCrewEntity) {
        crew.add(inGameCrewEntity);
        inGameCrewEntity.setPlane(this);
    }

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
