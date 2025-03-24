package pl.miloszgilga.ahms.network.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.miloszgilga.ahms.dto.SimpleMessageResDto;
import pl.miloszgilga.ahms.network.auth.reqdto.ActivateAccountReqDto;
import pl.miloszgilga.ahms.network.auth.reqdto.LoginReqDto;
import pl.miloszgilga.ahms.network.auth.reqdto.RefreshReqDto;
import pl.miloszgilga.ahms.network.auth.reqdto.RegisterReqDto;
import pl.miloszgilga.ahms.network.auth.resdto.JwtAuthenticationResDto;
import pl.miloszgilga.ahms.security.LoggedUser;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    ResponseEntity<JwtAuthenticationResDto> login(@RequestBody @Valid LoginReqDto reqDto) {
        return new ResponseEntity<>(authService.login(reqDto), HttpStatus.OK);
    }

    @PostMapping("/register")
    ResponseEntity<SimpleMessageResDto> register(@RequestBody @Valid RegisterReqDto reqDto) {
        return new ResponseEntity<>(authService.register(reqDto), HttpStatus.CREATED);
    }

    @PostMapping("/activate")
    ResponseEntity<SimpleMessageResDto> activate(@RequestBody @Valid ActivateAccountReqDto reqDto) {
        return new ResponseEntity<>(authService.activate(reqDto), HttpStatus.OK);
    }

    @PostMapping("/refresh")
    ResponseEntity<JwtAuthenticationResDto> refresh(@RequestBody @Valid RefreshReqDto reqDto) {
        return new ResponseEntity<>(authService.refresh(reqDto), HttpStatus.OK);
    }

    @GetMapping("/logout")
    ResponseEntity<SimpleMessageResDto> logout(HttpServletRequest req, @AuthenticationPrincipal LoggedUser loggedUser) {
        return new ResponseEntity<>(authService.logout(req, loggedUser), HttpStatus.OK);
    }
}
