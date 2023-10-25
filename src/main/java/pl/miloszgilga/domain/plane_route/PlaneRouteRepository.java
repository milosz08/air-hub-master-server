/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.domain.plane_route;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaneRouteRepository extends JpaRepository<PlaneRouteEntity, Long> {
    List<PlaneRouteEntity> findAllByPlane_Id(Long planeId);
}
