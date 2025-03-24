package pl.miloszgilga.ahms.network.workshop;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.miloszgilga.ahms.network.workshop.resdto.WorkshopPlaneStatsResDto;
import pl.miloszgilga.ahms.security.LoggedUser;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/workshop")
class WorkshopController {
    private final WorkshopService workshopService;

    @GetMapping("/planes")
    ResponseEntity<List<WorkshopPlaneStatsResDto>> getPlanes(@AuthenticationPrincipal LoggedUser loggedUser) {
        return new ResponseEntity<>(workshopService.getPlanes(loggedUser), HttpStatus.OK);
    }
}
