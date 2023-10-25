/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.network.game;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jmpsl.security.user.AuthUser;
import org.jmpsl.security.user.CurrentUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.miloszgilga.dto.SimpleMessageResDto;
import pl.miloszgilga.network.game.reqdto.GenerateStatsReqDto;
import pl.miloszgilga.network.game.resdto.GeneratedSendPlaneStatsResDto;
import pl.miloszgilga.network.game.resdto.InFlightPlaneResDto;
import pl.miloszgilga.network.game.resdto.PlanesWithRoutesResDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/game")
class GameController {
    private final GameService gameService;

    @GetMapping("/planes/routes")
    ResponseEntity<PlanesWithRoutesResDto> getPlanesWithRoutes(@CurrentUser AuthUser user) {
        return new ResponseEntity<>(gameService.getPlanesWithRoutes(user), HttpStatus.OK);
    }

    @GetMapping("/planes/flight")
    ResponseEntity<List<InFlightPlaneResDto>> getInFlightPlanes(@CurrentUser AuthUser user) {
        return new ResponseEntity<>(gameService.getInFlightPlanes(user), HttpStatus.OK);
    }

    @PostMapping("/stats")
    ResponseEntity<GeneratedSendPlaneStatsResDto> generateStats(
        @Valid @RequestBody GenerateStatsReqDto reqDto, @CurrentUser AuthUser user
    ) {
        return new ResponseEntity<>(gameService.generateStats(reqDto, user), HttpStatus.OK);
    }

    @PostMapping("/plane/send/{planeId}")
    ResponseEntity<SimpleMessageResDto> sendPlane(@PathVariable Long planeId, @CurrentUser AuthUser user) {
        return new ResponseEntity<>(gameService.sendPlane(planeId, user), HttpStatus.CREATED);
    }

    @DeleteMapping("/plane/revert/{planeId}")
    ResponseEntity<SimpleMessageResDto> revertPlane(@PathVariable Long planeId, @CurrentUser AuthUser user) {
        return new ResponseEntity<>(gameService.revertPlane(planeId, user), HttpStatus.OK);
    }
}
