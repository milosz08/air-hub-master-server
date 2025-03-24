package pl.miloszgilga.ahms.network.bought;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.miloszgilga.ahms.network.bought.resdto.InGamePlaneResDto;
import pl.miloszgilga.ahms.network.bought.resdto.InGameWorkerResDto;
import pl.miloszgilga.ahms.security.LoggedUser;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bought")
class BoughtController {
    private final BoughtService boughtService;

    @GetMapping("/planes")
    ResponseEntity<List<InGamePlaneResDto>> boughtPlanes(@AuthenticationPrincipal LoggedUser loggedUser) {
        return new ResponseEntity<>(boughtService.boughtPlanes(loggedUser), HttpStatus.OK);
    }

    @GetMapping("/workers")
    ResponseEntity<List<InGameWorkerResDto>> boughtWorkers(@AuthenticationPrincipal LoggedUser loggedUser) {
        return new ResponseEntity<>(boughtService.boughtWorkers(loggedUser), HttpStatus.OK);
    }
}
