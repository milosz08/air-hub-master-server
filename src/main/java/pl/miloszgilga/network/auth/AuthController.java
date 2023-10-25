/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.network.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jmpsl.security.user.AuthUser;
import org.jmpsl.security.user.CurrentUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.miloszgilga.dto.SimpleMessageResDto;
import pl.miloszgilga.network.auth.reqdto.ActivateAccountReqDto;
import pl.miloszgilga.network.auth.reqdto.LoginReqDto;
import pl.miloszgilga.network.auth.reqdto.RefreshReqDto;
import pl.miloszgilga.network.auth.reqdto.RegisterReqDto;
import pl.miloszgilga.network.auth.resdto.JwtAuthenticationResDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/auth")
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
    ResponseEntity<SimpleMessageResDto> logout(HttpServletRequest req, @CurrentUser AuthUser authUser) {
        return new ResponseEntity<>(authService.logout(req, authUser), HttpStatus.OK);
    }
}
