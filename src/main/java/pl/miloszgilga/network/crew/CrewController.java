/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.network.crew;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jmpsl.security.user.AuthUser;
import org.jmpsl.security.user.CurrentUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.miloszgilga.dto.SimpleMessageResDto;
import pl.miloszgilga.network.crew.reqdto.AddCrewReqDto;
import pl.miloszgilga.network.crew.resdto.CrewDataResDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/crew")
class CrewController {
    private final CrewService crewService;

    @GetMapping
    ResponseEntity<CrewDataResDto> getCrew(@CurrentUser AuthUser user) {
        return new ResponseEntity<>(crewService.getCrew(user), HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<SimpleMessageResDto> addCrew(@Valid @RequestBody AddCrewReqDto reqDto, @CurrentUser AuthUser user) {
        return new ResponseEntity<>(crewService.addCrew(reqDto, user), HttpStatus.CREATED);
    }
}
