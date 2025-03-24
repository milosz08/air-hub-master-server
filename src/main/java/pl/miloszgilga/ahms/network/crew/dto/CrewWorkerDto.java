package pl.miloszgilga.ahms.network.crew.dto;

import pl.miloszgilga.ahms.domain.in_game_worker_params.InGameWorkerParamEntity;
import pl.miloszgilga.ahms.utils.Utilities;

import java.util.Objects;

public record CrewWorkerDto(
    long id,
    String fullName,
    boolean available,
    int experience,
    int cooperation,
    int rebelliousness,
    int skills
) {
    public CrewWorkerDto(InGameWorkerParamEntity worker) {
        this(worker.getId(), Utilities.parseWorkerFullName(worker.getWorker()), Objects.isNull(worker.getAvailable()),
            worker.getExperience(), worker.getCooperation(), worker.getRebelliousness(), worker.getSkills());
    }
}
