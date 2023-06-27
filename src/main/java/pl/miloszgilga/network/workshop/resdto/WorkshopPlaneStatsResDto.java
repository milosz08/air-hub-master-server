/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: WorkshopPlaneStatsResDto.java
 * Last modified: 6/27/23, 3:08 AM
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

package pl.miloszgilga.network.workshop.resdto;

import pl.miloszgilga.domain.in_game_plane_params.InGamePlaneParamEntity;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public record WorkshopPlaneStatsResDto(
    String name,
    String categoryName,
    int engine,
    int landingGeer,
    int wings
) {
    public WorkshopPlaneStatsResDto(InGamePlaneParamEntity plane) {
        this(plane.getPlane().getName(), plane.getPlane().getCategory().getName(), plane.getEngine(),
            plane.getLandingGeer(), plane.getWings());
    }
}
