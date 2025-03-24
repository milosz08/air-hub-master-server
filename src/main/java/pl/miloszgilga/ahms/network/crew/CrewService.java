package pl.miloszgilga.ahms.network.crew;

import pl.miloszgilga.ahms.dto.SimpleMessageResDto;
import pl.miloszgilga.ahms.network.crew.reqdto.AddCrewReqDto;
import pl.miloszgilga.ahms.network.crew.resdto.CrewDataResDto;
import pl.miloszgilga.ahms.security.LoggedUser;

interface CrewService {
    CrewDataResDto getCrew(LoggedUser loggedUser);

    SimpleMessageResDto addCrew(AddCrewReqDto reqDto, LoggedUser loggedUser);
}
