/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: CrewInFlightDto.java
 * Last modified: 6/27/23, 2:31 AM
 * Project name: air-hub-master-server
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the License at
 *
 *     <http://www.apache.org/license/LICENSE-2.0>
 *
 * Unless required by applicable law or agreed to in writing, software distributed under
 * the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the license.
 */

package pl.miloszgilga.network.game.dto;

import pl.miloszgilga.utils.Utilities;
import pl.miloszgilga.domain.in_game_worker_params.InGameWorkerParamEntity;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public record CrewInFlightDto(
    String fullName,
    String categoryName
) {
    public CrewInFlightDto(InGameWorkerParamEntity worker) {
        this(Utilities.parseWorkerFullName(worker.getWorker()), worker.getWorker().getCategory().getName());
    }
}
