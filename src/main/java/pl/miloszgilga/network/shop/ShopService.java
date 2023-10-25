/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.network.shop;

import org.jmpsl.security.user.AuthUser;
import pl.miloszgilga.network.shop.resdto.ShopPlanesResDto;
import pl.miloszgilga.network.shop.resdto.ShopWorkersResDto;
import pl.miloszgilga.network.shop.resdto.TransactMoneyStatusResDto;

import java.util.List;

interface ShopService {
    List<ShopPlanesResDto> planes(AuthUser user);
    List<ShopWorkersResDto> workers(AuthUser user);
    TransactMoneyStatusResDto buyPlane(Long planeId, AuthUser user);
    TransactMoneyStatusResDto buyWorker(Long workerId, AuthUser user);
}
