package pl.miloszgilga.ahms.network.bought;

import pl.miloszgilga.ahms.network.bought.resdto.InGamePlaneResDto;
import pl.miloszgilga.ahms.network.bought.resdto.InGameWorkerResDto;
import pl.miloszgilga.ahms.security.LoggedUser;

import java.util.List;

interface BoughtService {
    List<InGamePlaneResDto> boughtPlanes(LoggedUser loggedUser);

    List<InGameWorkerResDto> boughtWorkers(LoggedUser loggedUser);
}
