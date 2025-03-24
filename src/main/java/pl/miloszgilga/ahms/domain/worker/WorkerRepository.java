package pl.miloszgilga.ahms.domain.worker;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkerRepository extends JpaRepository<WorkerEntity, Long> {
    Optional<WorkerEntity> findById(Long workerId);

    @Query(value = "from WorkerEntity e join fetch e.category")
    List<WorkerEntity> findAllLazillyLoadedWorkers();
}
