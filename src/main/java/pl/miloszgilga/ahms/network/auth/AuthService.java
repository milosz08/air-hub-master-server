package pl.miloszgilga.ahms.network.auth;

import jakarta.servlet.http.HttpServletRequest;
import pl.miloszgilga.ahms.dto.SimpleMessageResDto;
import pl.miloszgilga.ahms.network.auth.reqdto.ActivateAccountReqDto;
import pl.miloszgilga.ahms.network.auth.reqdto.LoginReqDto;
import pl.miloszgilga.ahms.network.auth.reqdto.RefreshReqDto;
import pl.miloszgilga.ahms.network.auth.reqdto.RegisterReqDto;
import pl.miloszgilga.ahms.network.auth.resdto.JwtAuthenticationResDto;
import pl.miloszgilga.ahms.security.LoggedUser;

interface AuthService {
    JwtAuthenticationResDto login(LoginReqDto reqDto);

    SimpleMessageResDto register(RegisterReqDto reqDto);

    SimpleMessageResDto activate(ActivateAccountReqDto reqDto);

    JwtAuthenticationResDto refresh(RefreshReqDto reqDto);

    SimpleMessageResDto logout(HttpServletRequest req, LoggedUser loggedUser);
}
