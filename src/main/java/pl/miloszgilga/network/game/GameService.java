/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.network.game;

import org.jmpsl.security.user.AuthUser;
import pl.miloszgilga.dto.SimpleMessageResDto;
import pl.miloszgilga.network.game.reqdto.GenerateStatsReqDto;
import pl.miloszgilga.network.game.resdto.GeneratedSendPlaneStatsResDto;
import pl.miloszgilga.network.game.resdto.InFlightPlaneResDto;
import pl.miloszgilga.network.game.resdto.PlanesWithRoutesResDto;

import java.util.List;

interface GameService {
    PlanesWithRoutesResDto getPlanesWithRoutes(AuthUser user);
    List<InFlightPlaneResDto> getInFlightPlanes(AuthUser user);
    GeneratedSendPlaneStatsResDto generateStats(GenerateStatsReqDto reqDto, AuthUser user);
    SimpleMessageResDto sendPlane(Long planeId, AuthUser user);
    SimpleMessageResDto revertPlane(Long planeId, AuthUser user);
}
