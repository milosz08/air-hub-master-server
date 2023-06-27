/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: CrewController.java
 * Last modified: 6/27/23, 2:35 AM
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

package pl.miloszgilga.network.crew;

import lombok.RequiredArgsConstructor;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.jmpsl.security.user.AuthUser;
import org.jmpsl.security.user.CurrentUser;

import pl.miloszgilga.dto.SimpleMessageResDto;
import pl.miloszgilga.network.crew.reqdto.AddCrewReqDto;
import pl.miloszgilga.network.crew.resdto.CrewDataResDto;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/crew")
public class CrewController {

    private final ICrewService crewService;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping
    ResponseEntity<CrewDataResDto> getCrew(@CurrentUser AuthUser user) {
        return new ResponseEntity<>(crewService.getCrew(user), HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<SimpleMessageResDto> addCrew(@Valid @RequestBody AddCrewReqDto reqDto, @CurrentUser AuthUser user) {
        return new ResponseEntity<>(crewService.addCrew(reqDto, user), HttpStatus.CREATED);
    }
}
