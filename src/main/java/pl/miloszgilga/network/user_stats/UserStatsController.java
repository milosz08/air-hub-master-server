/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.network.user_stats;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jmpsl.security.user.AuthUser;
import org.jmpsl.security.user.CurrentUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.miloszgilga.network.user_stats.reqdto.UpdateExpReqDto;
import pl.miloszgilga.network.user_stats.reqdto.UpdateLevelReqDto;
import pl.miloszgilga.network.user_stats.reqdto.UpdateMoneyReqDto;
import pl.miloszgilga.network.user_stats.resdto.UpdateExpResDto;
import pl.miloszgilga.network.user_stats.resdto.UpdateLevelResDto;
import pl.miloszgilga.network.user_stats.resdto.UpdateMoneyResDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/user/stats")
class UserStatsController {
    private final UserStatsService userStatsService;

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
