/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.network.auth;

import jakarta.servlet.http.HttpServletRequest;
import org.jmpsl.security.user.AuthUser;
import pl.miloszgilga.dto.SimpleMessageResDto;
import pl.miloszgilga.network.auth.reqdto.ActivateAccountReqDto;
import pl.miloszgilga.network.auth.reqdto.LoginReqDto;
import pl.miloszgilga.network.auth.reqdto.RefreshReqDto;
import pl.miloszgilga.network.auth.reqdto.RegisterReqDto;
import pl.miloszgilga.network.auth.resdto.JwtAuthenticationResDto;

interface AuthService {
    JwtAuthenticationResDto login(LoginReqDto reqDto);
    SimpleMessageResDto register(RegisterReqDto reqDto);
    SimpleMessageResDto activate(ActivateAccountReqDto reqDto);
    JwtAuthenticationResDto refresh(RefreshReqDto reqDto);
    SimpleMessageResDto logout(HttpServletRequest req, AuthUser authUser);
}
