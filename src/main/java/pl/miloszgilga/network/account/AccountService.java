/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.network.account;

import jakarta.servlet.http.HttpServletRequest;
import org.jmpsl.security.user.AuthUser;
import pl.miloszgilga.dto.SimpleMessageResDto;
import pl.miloszgilga.network.account.reqdto.*;
import pl.miloszgilga.network.account.resdto.AccountDetailsResDto;
import pl.miloszgilga.network.account.resdto.UpdatedEmailResDto;
import pl.miloszgilga.network.account.resdto.UpdatedLoginResDto;
import pl.miloszgilga.network.account.resdto.UpdatedNameResDto;

interface AccountService {
    AccountDetailsResDto details(AuthUser authUser);
    UpdatedNameResDto updateName(UpdateNameReqDto reqDto, AuthUser authUser);
    UpdatedLoginResDto updateLogin(HttpServletRequest req, UpdateLoginReqDto reqDto, AuthUser authUser);
    UpdatedEmailResDto updateEmail(UpdateEmailReqDto reqDto, AuthUser authUser);
    SimpleMessageResDto updatePassword(UpdatePasswordReqDto reqDto, AuthUser authUser);
    SimpleMessageResDto delete(DeleteAccountReqDto reqDto, AuthUser authUser);
}
