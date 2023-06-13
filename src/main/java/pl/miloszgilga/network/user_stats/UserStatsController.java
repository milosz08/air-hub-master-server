/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: UserStatsController.java
 * Last modified: 6/13/23, 10:22 PM
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

package pl.miloszgilga.network.user_stats;

import lombok.RequiredArgsConstructor;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.jmpsl.security.user.AuthUser;
import org.jmpsl.security.user.CurrentUser;

import pl.miloszgilga.network.user_stats.reqdto.UpdateExpReqDto;
import pl.miloszgilga.network.user_stats.reqdto.UpdateLevelReqDto;
import pl.miloszgilga.network.user_stats.reqdto.UpdateMoneyReqDto;
import pl.miloszgilga.network.user_stats.resdto.UpdateExpResDto;
import pl.miloszgilga.network.user_stats.resdto.UpdateLevelResDto;
import pl.miloszgilga.network.user_stats.resdto.UpdateMoneyResDto;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/user/stats")
class UserStatsController {

    private final IUserStatsService userStatsService;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PatchMapping("/level")
    ResponseEntity<UpdateLevelResDto> level(@RequestBody @Valid UpdateLevelReqDto reqDto, @CurrentUser AuthUser user) {
        return new ResponseEntity<>(userStatsService.level(reqDto, user), HttpStatus.OK);
    }

    @PatchMapping("/exp")
    ResponseEntity<UpdateExpResDto> exp(@RequestBody @Valid UpdateExpReqDto reqDto, @CurrentUser AuthUser user) {
        return new ResponseEntity<>(userStatsService.exp(reqDto, user), HttpStatus.OK);
    }

    @PatchMapping("/money")
    ResponseEntity<UpdateMoneyResDto> money(@RequestBody @Valid UpdateMoneyReqDto reqDto, @CurrentUser AuthUser user) {
        return new ResponseEntity<>(userStatsService.money(reqDto, user), HttpStatus.OK);
    }
}
