package pl.miloszgilga.ahms.network.shop;

import pl.miloszgilga.ahms.network.shop.resdto.ShopPlanesResDto;
import pl.miloszgilga.ahms.network.shop.resdto.ShopWorkersResDto;
import pl.miloszgilga.ahms.network.shop.resdto.TransactMoneyStatusResDto;
import pl.miloszgilga.ahms.security.LoggedUser;

import java.util.List;

interface ShopService {
    List<ShopPlanesResDto> planes(LoggedUser loggedUser);

    List<ShopWorkersResDto> workers(LoggedUser loggedUser);

    TransactMoneyStatusResDto buyPlane(Long planeId, LoggedUser loggedUser);

    TransactMoneyStatusResDto buyWorker(Long workerId, LoggedUser loggedUser);
}
