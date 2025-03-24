package pl.miloszgilga.ahms.domain.plane;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaneRepository extends JpaRepository<PlaneEntity, Long> {
    Optional<PlaneEntity> findById(Long planeId);

    @Query(value = "from PlaneEntity e join fetch e.category")
    List<PlaneEntity> findAllLazillyLoadedPlanes();
}
