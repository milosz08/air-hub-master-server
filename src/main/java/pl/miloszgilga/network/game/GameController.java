/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: GameController.java
 * Last modified: 6/26/23, 4:06 AM
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

package pl.miloszgilga.network.game;

import lombok.RequiredArgsConstructor;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.jmpsl.security.user.AuthUser;
import org.jmpsl.security.user.CurrentUser;

import pl.miloszgilga.dto.SimpleMessageResDto;
import pl.miloszgilga.network.game.reqdto.GenerateStatsReqDto;
import pl.miloszgilga.network.game.resdto.InFlightPlaneResDto;
import pl.miloszgilga.network.game.resdto.PlanesWithRoutesResDto;
import pl.miloszgilga.network.game.resdto.GeneratedSendPlaneStatsResDto;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/game")
public class GameController {

    private final IGameService gameService;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
