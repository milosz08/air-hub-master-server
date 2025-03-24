package pl.miloszgilga.ahms.network.game;

import pl.miloszgilga.ahms.dto.SimpleMessageResDto;
import pl.miloszgilga.ahms.network.game.reqdto.GenerateStatsReqDto;
import pl.miloszgilga.ahms.network.game.resdto.GeneratedSendPlaneStatsResDto;
import pl.miloszgilga.ahms.network.game.resdto.InFlightPlaneResDto;
import pl.miloszgilga.ahms.network.game.resdto.PlanesWithRoutesResDto;
import pl.miloszgilga.ahms.security.LoggedUser;

import java.util.List;

interface GameService {
    PlanesWithRoutesResDto getPlanesWithRoutes(LoggedUser loggedUser);

    List<InFlightPlaneResDto> getInFlightPlanes(LoggedUser loggedUser);

    GeneratedSendPlaneStatsResDto generateStats(GenerateStatsReqDto reqDto, LoggedUser loggedUser);

    SimpleMessageResDto sendPlane(Long planeId, LoggedUser loggedUser);

    SimpleMessageResDto revertPlane(Long planeId, LoggedUser loggedUser);
}
