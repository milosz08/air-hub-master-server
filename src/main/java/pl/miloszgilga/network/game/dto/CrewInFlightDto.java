/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.network.game.dto;

import pl.miloszgilga.domain.in_game_worker_params.InGameWorkerParamEntity;
import pl.miloszgilga.utils.Utilities;

public record CrewInFlightDto(
    String fullName,
    String categoryName
) {
    public CrewInFlightDto(InGameWorkerParamEntity worker) {
        this(Utilities.parseWorkerFullName(worker.getWorker()), worker.getWorker().getCategory().getName());
    }
}
