package pl.miloszgilga.ahms.network.shop;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.miloszgilga.ahms.network.shop.resdto.ShopPlanesResDto;
import pl.miloszgilga.ahms.network.shop.resdto.ShopWorkersResDto;
import pl.miloszgilga.ahms.network.shop.resdto.TransactMoneyStatusResDto;
import pl.miloszgilga.ahms.security.LoggedUser;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/shop")
class ShopController {
    private final ShopService shopService;

    @GetMapping("/planes")
    ResponseEntity<List<ShopPlanesResDto>> planes(@AuthenticationPrincipal LoggedUser loggedUser) {
        return new ResponseEntity<>(shopService.planes(loggedUser), HttpStatus.OK);
    }

    @GetMapping("/workers")
    ResponseEntity<List<ShopWorkersResDto>> workers(@AuthenticationPrincipal LoggedUser loggedUser) {
        return new ResponseEntity<>(shopService.workers(loggedUser), HttpStatus.OK);
    }

    @PostMapping("/buy/plane/{planeId}")
    ResponseEntity<TransactMoneyStatusResDto> buyPlane(
        @PathVariable Long planeId,
        @AuthenticationPrincipal LoggedUser loggedUser
    ) {
        return new ResponseEntity<>(shopService.buyPlane(planeId, loggedUser), HttpStatus.CREATED);
    }

    @PostMapping("/buy/worker/{workerId}")
    ResponseEntity<TransactMoneyStatusResDto> buyWorker(
        @PathVariable Long workerId,
        @AuthenticationPrincipal LoggedUser loggedUser
    ) {
        return new ResponseEntity<>(shopService.buyWorker(workerId, loggedUser), HttpStatus.CREATED);
    }
}
