package pl.miloszgilga.ahms.domain.workers_shop;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkerShopRepository extends JpaRepository<WorkerShopEntity, Long> {
    @Query(value = "from WorkerShopEntity e inner join e.user u join fetch e.worker where u.id = :uId")
    List<WorkerShopEntity> findByUseId(@Param("uId") Long uId);

    @Query(value = "from WorkerShopEntity e inner join e.user u inner join e.worker w where w.id = :wId and u.id = :uId")
    Optional<WorkerShopEntity> findByWorkerIdAndUserId(@Param("wId") Long wId, @Param("uId") Long uId);
}
