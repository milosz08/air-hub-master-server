/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: InFlightPlaneResDto.java
 * Last modified: 6/27/23, 2:32 AM
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

package pl.miloszgilga.network.game.resdto;

import java.util.List;
import java.time.ZonedDateTime;

import pl.miloszgilga.network.game.dto.CrewInFlightDto;
import pl.miloszgilga.domain.in_game_plane_params.InGamePlaneParamEntity;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public record InFlightPlaneResDto(
    String name,
    String categoryName,
    ZonedDateTime arrival,
    List<CrewInFlightDto> workers
) {
    public InFlightPlaneResDto(InGamePlaneParamEntity plane, List<CrewInFlightDto> workers) {
        this(plane.getPlane().getName(), plane.getPlane().getCategory().getName(), plane.getAvailable(), workers);
    }
}
