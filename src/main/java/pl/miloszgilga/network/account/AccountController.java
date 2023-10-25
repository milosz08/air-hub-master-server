/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.network.account;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jmpsl.security.user.AuthUser;
import org.jmpsl.security.user.CurrentUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.miloszgilga.dto.SimpleMessageResDto;
import pl.miloszgilga.network.account.reqdto.*;
import pl.miloszgilga.network.account.resdto.AccountDetailsResDto;
import pl.miloszgilga.network.account.resdto.UpdatedEmailResDto;
import pl.miloszgilga.network.account.resdto.UpdatedLoginResDto;
import pl.miloszgilga.network.account.resdto.UpdatedNameResDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/account")
class AccountController {
    private final AccountService accountService;

    @GetMapping("/details")
    ResponseEntity<AccountDetailsResDto> details(@CurrentUser AuthUser authUser) {
        return new ResponseEntity<>(accountService.details(authUser), HttpStatus.OK);
    }

    @PatchMapping("/update/name")
    ResponseEntity<UpdatedNameResDto> updateName(
        @RequestBody @Valid UpdateNameReqDto reqDto, @CurrentUser AuthUser authUser
    ) {
        return new ResponseEntity<>(accountService.updateName(reqDto, authUser), HttpStatus.OK);
    }

    @PatchMapping("/update/login")
    ResponseEntity<UpdatedLoginResDto> updateLogin(
        HttpServletRequest req, @RequestBody @Valid UpdateLoginReqDto reqDto, @CurrentUser AuthUser authUser
    ) {
        return new ResponseEntity<>(accountService.updateLogin(req, reqDto, authUser), HttpStatus.OK);
    }

    @PatchMapping("/update/email")
    ResponseEntity<UpdatedEmailResDto> updateEmail(
        @RequestBody @Valid UpdateEmailReqDto reqDto, @CurrentUser AuthUser authUser
    ) {
        return new ResponseEntity<>(accountService.updateEmail(reqDto, authUser), HttpStatus.OK);
    }

    @PatchMapping("/update/password")
    ResponseEntity<SimpleMessageResDto> updatePassword(
        @RequestBody @Valid UpdatePasswordReqDto reqDto, @CurrentUser AuthUser authUser
    ) {
        return new ResponseEntity<>(accountService.updatePassword(reqDto, authUser), HttpStatus.OK);
    }

    @DeleteMapping
    ResponseEntity<SimpleMessageResDto> delete(
        @RequestBody @Valid DeleteAccountReqDto reqDto, @CurrentUser AuthUser authUser
    ) {
        return new ResponseEntity<>(accountService.delete(reqDto, authUser), HttpStatus.OK);
    }
}
