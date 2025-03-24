package pl.miloszgilga.ahms.network.renew_password;

import pl.miloszgilga.ahms.dto.SimpleMessageResDto;
import pl.miloszgilga.ahms.network.renew_password.reqdto.ChangePasswordValidatorReqDto;
import pl.miloszgilga.ahms.network.renew_password.reqdto.RequestChangePasswordReqDto;

interface RenewPasswordService {
    SimpleMessageResDto request(RequestChangePasswordReqDto reqDto);

    SimpleMessageResDto change(ChangePasswordValidatorReqDto reqDto);
}
