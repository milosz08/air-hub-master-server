package pl.miloszgilga.ahms.network.crew;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.miloszgilga.ahms.dto.SimpleMessageResDto;
import pl.miloszgilga.ahms.network.crew.reqdto.AddCrewReqDto;
import pl.miloszgilga.ahms.network.crew.resdto.CrewDataResDto;
import pl.miloszgilga.ahms.security.LoggedUser;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/crew")
class CrewController {
    private final CrewService crewService;

    @GetMapping
    ResponseEntity<CrewDataResDto> getCrew(@AuthenticationPrincipal LoggedUser loggedUser) {
        return new ResponseEntity<>(crewService.getCrew(loggedUser), HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<SimpleMessageResDto> addCrew(
        @Valid @RequestBody AddCrewReqDto reqDto,
        @AuthenticationPrincipal LoggedUser loggedUser
    ) {
        return new ResponseEntity<>(crewService.addCrew(reqDto, loggedUser), HttpStatus.CREATED);
    }
}
