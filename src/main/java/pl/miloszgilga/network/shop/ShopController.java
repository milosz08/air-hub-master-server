/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.network.shop;

import lombok.RequiredArgsConstructor;
import org.jmpsl.security.user.AuthUser;
import org.jmpsl.security.user.CurrentUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.miloszgilga.network.shop.resdto.ShopPlanesResDto;
import pl.miloszgilga.network.shop.resdto.ShopWorkersResDto;
import pl.miloszgilga.network.shop.resdto.TransactMoneyStatusResDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/shop")
class ShopController {
    private final ShopService shopService;

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
