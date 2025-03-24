package pl.miloszgilga.ahms.network.game;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.miloszgilga.ahms.dto.SimpleMessageResDto;
import pl.miloszgilga.ahms.network.game.reqdto.GenerateStatsReqDto;
import pl.miloszgilga.ahms.network.game.resdto.GeneratedSendPlaneStatsResDto;
import pl.miloszgilga.ahms.network.game.resdto.InFlightPlaneResDto;
import pl.miloszgilga.ahms.network.game.resdto.PlanesWithRoutesResDto;
import pl.miloszgilga.ahms.security.LoggedUser;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/game")
class GameController {
    private final GameService gameService;

    @GetMapping("/planes/routes")
    ResponseEntity<PlanesWithRoutesResDto> getPlanesWithRoutes(@AuthenticationPrincipal LoggedUser loggedUser) {
        return new ResponseEntity<>(gameService.getPlanesWithRoutes(loggedUser), HttpStatus.OK);
    }

    @GetMapping("/planes/flight")
    ResponseEntity<List<InFlightPlaneResDto>> getInFlightPlanes(@AuthenticationPrincipal LoggedUser loggedUser) {
        return new ResponseEntity<>(gameService.getInFlightPlanes(loggedUser), HttpStatus.OK);
    }

    @PostMapping("/stats")
    ResponseEntity<GeneratedSendPlaneStatsResDto> generateStats(
        @Valid @RequestBody GenerateStatsReqDto reqDto,
        @AuthenticationPrincipal LoggedUser loggedUser
    ) {
        return new ResponseEntity<>(gameService.generateStats(reqDto, loggedUser), HttpStatus.OK);
    }

    @PostMapping("/plane/send/{planeId}")
    ResponseEntity<SimpleMessageResDto> sendPlane(
        @PathVariable Long planeId,
        @AuthenticationPrincipal LoggedUser loggedUser
    ) {
        return new ResponseEntity<>(gameService.sendPlane(planeId, loggedUser), HttpStatus.CREATED);
    }

    @DeleteMapping("/plane/revert/{planeId}")
    ResponseEntity<SimpleMessageResDto> revertPlane(
        @PathVariable Long planeId,
        @AuthenticationPrincipal LoggedUser loggedUser
    ) {
        return new ResponseEntity<>(gameService.revertPlane(planeId, loggedUser), HttpStatus.OK);
    }
}
