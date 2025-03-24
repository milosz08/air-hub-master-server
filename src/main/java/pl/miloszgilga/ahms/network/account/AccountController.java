package pl.miloszgilga.ahms.network.account;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.miloszgilga.ahms.dto.SimpleMessageResDto;
import pl.miloszgilga.ahms.network.account.reqdto.*;
import pl.miloszgilga.ahms.network.account.resdto.AccountDetailsResDto;
import pl.miloszgilga.ahms.network.account.resdto.UpdatedEmailResDto;
import pl.miloszgilga.ahms.network.account.resdto.UpdatedLoginResDto;
import pl.miloszgilga.ahms.network.account.resdto.UpdatedNameResDto;
import pl.miloszgilga.ahms.security.LoggedUser;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account")
class AccountController {
    private final AccountService accountService;

    @GetMapping("/details")
    ResponseEntity<AccountDetailsResDto> details(@AuthenticationPrincipal LoggedUser loggedUser) {
        return new ResponseEntity<>(accountService.details(loggedUser), HttpStatus.OK);
    }

    @PatchMapping("/update/name")
    ResponseEntity<UpdatedNameResDto> updateName(
        @RequestBody @Valid UpdateNameReqDto reqDto,
        @AuthenticationPrincipal LoggedUser loggedUser
    ) {
        return new ResponseEntity<>(accountService.updateName(reqDto, loggedUser), HttpStatus.OK);
    }

    @PatchMapping("/update/login")
    ResponseEntity<UpdatedLoginResDto> updateLogin(
        HttpServletRequest req, @RequestBody @Valid UpdateLoginReqDto reqDto,
        @AuthenticationPrincipal LoggedUser loggedUser
    ) {
        return new ResponseEntity<>(accountService.updateLogin(req, reqDto, loggedUser), HttpStatus.OK);
    }

    @PatchMapping("/update/email")
    ResponseEntity<UpdatedEmailResDto> updateEmail(
        @RequestBody @Valid UpdateEmailReqDto reqDto,
        @AuthenticationPrincipal LoggedUser loggedUser
    ) {
        return new ResponseEntity<>(accountService.updateEmail(reqDto, loggedUser), HttpStatus.OK);
    }

    @PatchMapping("/update/password")
    ResponseEntity<SimpleMessageResDto> updatePassword(
        @RequestBody @Valid UpdatePasswordReqDto reqDto,
        @AuthenticationPrincipal LoggedUser loggedUser
    ) {
        return new ResponseEntity<>(accountService.updatePassword(reqDto, loggedUser), HttpStatus.OK);
    }

    @DeleteMapping
    ResponseEntity<SimpleMessageResDto> delete(
        @RequestBody @Valid DeleteAccountReqDto reqDto,
        @AuthenticationPrincipal LoggedUser loggedUser
    ) {
        return new ResponseEntity<>(accountService.delete(reqDto, loggedUser), HttpStatus.OK);
    }
}
