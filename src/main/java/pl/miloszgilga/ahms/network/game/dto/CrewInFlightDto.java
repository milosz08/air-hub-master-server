package pl.miloszgilga.ahms.network.game.dto;

import pl.miloszgilga.ahms.domain.in_game_worker_params.InGameWorkerParamEntity;
import pl.miloszgilga.ahms.utils.Utilities;

public record CrewInFlightDto(
    String fullName,
    String categoryName
) {
    public CrewInFlightDto(InGameWorkerParamEntity worker) {
        this(Utilities.parseWorkerFullName(worker.getWorker()), worker.getWorker().getCategory().getName());
    }
}
