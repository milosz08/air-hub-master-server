package pl.miloszgilga.ahms.network.bought;

import lombok.RequiredArgsConstructor;
import org.jmpsl.security.user.AuthUser;
import org.jmpsl.security.user.CurrentUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.miloszgilga.network.bought.resdto.InGamePlaneResDto;
import pl.miloszgilga.network.bought.resdto.InGameWorkerResDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/bought")
class BoughtController {
    private final BoughtService boughtService;

    @GetMapping("/planes")
    ResponseEntity<List<InGamePlaneResDto>> boughtPlanes(@CurrentUser AuthUser user) {
        return new ResponseEntity<>(boughtService.boughtPlanes(user), HttpStatus.OK);
    }

    @GetMapping("/workers")
    ResponseEntity<List<InGameWorkerResDto>> boughtWorkers(@CurrentUser AuthUser user) {
        return new ResponseEntity<>(boughtService.boughtWorkers(user), HttpStatus.OK);
    }
}
