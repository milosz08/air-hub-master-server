/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: IShopService.java
 * Last modified: 6/23/23, 12:01 AM
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

package pl.miloszgilga.network.shop;

import java.util.List;

import org.jmpsl.security.user.AuthUser;

import pl.miloszgilga.network.shop.resdto.ShopPlanesResDto;
import pl.miloszgilga.network.shop.resdto.ShopWorkersResDto;
import pl.miloszgilga.network.shop.resdto.TransactMoneyStatusResDto;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public interface IShopService {
    List<ShopPlanesResDto> planes(AuthUser user);
    List<ShopWorkersResDto> workers(AuthUser user);
    TransactMoneyStatusResDto buyPlane(Long planeId, AuthUser user);
    TransactMoneyStatusResDto buyWorker(Long workerId, AuthUser user);
}
