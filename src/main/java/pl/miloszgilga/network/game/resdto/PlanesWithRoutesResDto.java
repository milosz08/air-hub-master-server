/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: PlanesWithRoutesResDtp.java
 * Last modified: 6/26/23, 4:54 AM
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

import lombok.Builder;
import pl.miloszgilga.network.game.dto.PlaneWithRoutesDto;

import java.util.List;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

@Builder
public record PlanesWithRoutesResDto(
    List<String> planesCategories,
    List<PlaneWithRoutesDto> planes
) {
}
