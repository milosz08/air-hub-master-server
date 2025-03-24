package pl.miloszgilga.ahms.domain.plane_route;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaneRouteRepository extends JpaRepository<PlaneRouteEntity, Long> {
    List<PlaneRouteEntity> findAllByPlane_Id(Long planeId);
}
