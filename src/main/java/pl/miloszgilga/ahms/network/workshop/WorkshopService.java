package pl.miloszgilga.ahms.network.workshop;

import pl.miloszgilga.ahms.network.workshop.resdto.WorkshopPlaneStatsResDto;
import pl.miloszgilga.ahms.security.LoggedUser;

import java.util.List;

interface WorkshopService {
    List<WorkshopPlaneStatsResDto> getPlanes(LoggedUser loggedUser);
}
