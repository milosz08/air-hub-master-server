/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.network.renew_password;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.miloszgilga.dto.SimpleMessageResDto;
import pl.miloszgilga.network.renew_password.reqdto.ChangePasswordValidatorReqDto;
import pl.miloszgilga.network.renew_password.reqdto.RequestChangePasswordReqDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/renew-password")
class RenewPasswordController {
    private final RenewPasswordService renewPasswordService;

    @PostMapping("/request")
    ResponseEntity<SimpleMessageResDto> request(@RequestBody @Valid RequestChangePasswordReqDto reqDto) {
        return new ResponseEntity<>(renewPasswordService.request(reqDto), HttpStatus.OK);
    }

    @PostMapping("/change")
    ResponseEntity<SimpleMessageResDto> change(@RequestBody @Valid ChangePasswordValidatorReqDto reqDto) {
        return new ResponseEntity<>(renewPasswordService.change(reqDto), HttpStatus.OK);
    }
}
