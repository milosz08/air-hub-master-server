/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.network.renew_password;

import pl.miloszgilga.dto.SimpleMessageResDto;
import pl.miloszgilga.network.renew_password.reqdto.ChangePasswordValidatorReqDto;
import pl.miloszgilga.network.renew_password.reqdto.RequestChangePasswordReqDto;

interface RenewPasswordService {
    SimpleMessageResDto request(RequestChangePasswordReqDto reqDto);
    SimpleMessageResDto change(ChangePasswordValidatorReqDto reqDto);
}
