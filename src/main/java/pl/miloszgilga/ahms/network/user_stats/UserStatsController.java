package pl.miloszgilga.ahms.network.user_stats;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.miloszgilga.ahms.network.user_stats.reqdto.UpdateExpReqDto;
import pl.miloszgilga.ahms.network.user_stats.reqdto.UpdateLevelReqDto;
import pl.miloszgilga.ahms.network.user_stats.reqdto.UpdateMoneyReqDto;
import pl.miloszgilga.ahms.network.user_stats.resdto.UpdateExpResDto;
import pl.miloszgilga.ahms.network.user_stats.resdto.UpdateLevelResDto;
import pl.miloszgilga.ahms.network.user_stats.resdto.UpdateMoneyResDto;
import pl.miloszgilga.ahms.security.LoggedUser;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/stats")
class UserStatsController {
    private final UserStatsService userStatsService;

    @PatchMapping("/level")
    ResponseEntity<UpdateLevelResDto> level(
        @RequestBody @Valid UpdateLevelReqDto reqDto,
        @AuthenticationPrincipal LoggedUser loggedUser
    ) {
        return new ResponseEntity<>(userStatsService.level(reqDto, loggedUser), HttpStatus.OK);
    }

    @PatchMapping("/exp")
    ResponseEntity<UpdateExpResDto> exp(
        @RequestBody @Valid UpdateExpReqDto reqDto,
        @AuthenticationPrincipal LoggedUser loggedUser
    ) {
        return new ResponseEntity<>(userStatsService.exp(reqDto, loggedUser), HttpStatus.OK);
    }

    @PatchMapping("/money")
    ResponseEntity<UpdateMoneyResDto> money(
        @RequestBody @Valid UpdateMoneyReqDto reqDto,
        @AuthenticationPrincipal LoggedUser loggedUser
    ) {
        return new ResponseEntity<>(userStatsService.money(reqDto, loggedUser), HttpStatus.OK);
    }
}
