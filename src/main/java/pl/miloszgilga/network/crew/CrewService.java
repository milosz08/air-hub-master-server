/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.network.crew;

import org.jmpsl.security.user.AuthUser;
import pl.miloszgilga.dto.SimpleMessageResDto;
import pl.miloszgilga.network.crew.reqdto.AddCrewReqDto;
import pl.miloszgilga.network.crew.resdto.CrewDataResDto;

interface CrewService {
    CrewDataResDto getCrew(AuthUser user);
    SimpleMessageResDto addCrew(AddCrewReqDto reqDto, AuthUser user);
}
