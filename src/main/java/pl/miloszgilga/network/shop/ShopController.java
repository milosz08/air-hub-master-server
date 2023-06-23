/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: ShopController.java
 * Last modified: 6/23/23, 12:00 AM
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

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.jmpsl.security.user.AuthUser;
import org.jmpsl.security.user.CurrentUser;

import pl.miloszgilga.network.shop.resdto.ShopPlanesResDto;
import pl.miloszgilga.network.shop.resdto.ShopWorkersResDto;
import pl.miloszgilga.network.shop.resdto.TransactMoneyStatusResDto;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/shop")
class ShopController {

    private final IShopService shopService;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping("/planes")
    ResponseEntity<List<ShopPlanesResDto>> planes(@CurrentUser AuthUser user) {
        return new ResponseEntity<>(shopService.planes(user), HttpStatus.OK);
    }

    @GetMapping("/workers")
    ResponseEntity<List<ShopWorkersResDto>> workers(@CurrentUser AuthUser user) {
        return new ResponseEntity<>(shopService.workers(user), HttpStatus.OK);
    }

    @PostMapping("/buy/plane/{planeId}")
    ResponseEntity<TransactMoneyStatusResDto> buyPlane(@PathVariable Long planeId, @CurrentUser AuthUser user) {
        return new ResponseEntity<>(shopService.buyPlane(planeId, user), HttpStatus.CREATED);
    }

    @PostMapping("/buy/worker/{workerId}")
    ResponseEntity<TransactMoneyStatusResDto> buyWorker(@PathVariable Long workerId, @CurrentUser AuthUser user) {
        return new ResponseEntity<>(shopService.buyWorker(workerId, user), HttpStatus.CREATED);
    }
}
