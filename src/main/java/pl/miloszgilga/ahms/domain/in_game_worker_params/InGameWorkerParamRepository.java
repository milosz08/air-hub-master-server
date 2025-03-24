package pl.miloszgilga.ahms.domain.in_game_worker_params;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface InGameWorkerParamRepository extends JpaRepository<InGameWorkerParamEntity, Long> {
    List<InGameWorkerParamEntity> findAllByUser_Id(Long userId);

    List<InGameWorkerParamEntity> findAllByIdInAndUser_Id(List<Long> workerIds, Long userId);

    boolean existsByIdAndUser_Id(Long workerId, Long userId);

    @Query(value = """
        from InGameWorkerParamEntity e where e.id not in
        (select c.worker.id from InGameCrewEntity c) and e.user.id = :uId and e.available = null
        """)
    List<InGameWorkerParamEntity> findAllExcInJoiningTable(@Param("uId") Long uId);

    @Modifying
    @Query(value = """
        update InGameWorkerParamEntity e set e.available = null
        where e.available is not null and e.available < :futureExpierd
        """)
    void revertAvailableWorkersState(@Param("futureExpierd") LocalDateTime futureExpired);
}
