/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: IGameService.java
 * Last modified: 6/26/23, 4:06 AM
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

package pl.miloszgilga.network.game;

import org.jmpsl.security.user.AuthUser;

import java.util.List;

import pl.miloszgilga.dto.SimpleMessageResDto;
import pl.miloszgilga.network.game.reqdto.GenerateStatsReqDto;
import pl.miloszgilga.network.game.resdto.InFlightPlaneResDto;
import pl.miloszgilga.network.game.resdto.PlanesWithRoutesResDto;
import pl.miloszgilga.network.game.resdto.GeneratedSendPlaneStatsResDto;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public interface IGameService {
    PlanesWithRoutesResDto getPlanesWithRoutes(AuthUser user);
    List<InFlightPlaneResDto> getInFlightPlanes(AuthUser user);
    GeneratedSendPlaneStatsResDto generateStats(GenerateStatsReqDto reqDto, AuthUser user);
    SimpleMessageResDto sendPlane(Long planeId, AuthUser user);
    SimpleMessageResDto revertPlane(Long planeId, AuthUser user);
}
