package pl.miloszgilga.ahms.network.account;

import jakarta.servlet.http.HttpServletRequest;
import pl.miloszgilga.ahms.dto.SimpleMessageResDto;
import pl.miloszgilga.ahms.network.account.reqdto.*;
import pl.miloszgilga.ahms.network.account.resdto.AccountDetailsResDto;
import pl.miloszgilga.ahms.network.account.resdto.UpdatedEmailResDto;
import pl.miloszgilga.ahms.network.account.resdto.UpdatedLoginResDto;
import pl.miloszgilga.ahms.network.account.resdto.UpdatedNameResDto;
import pl.miloszgilga.ahms.security.LoggedUser;

interface AccountService {
    AccountDetailsResDto details(LoggedUser loggedUser);

    UpdatedNameResDto updateName(UpdateNameReqDto reqDto, LoggedUser loggedUser);

    UpdatedLoginResDto updateLogin(HttpServletRequest req, UpdateLoginReqDto reqDto, LoggedUser loggedUser);

    UpdatedEmailResDto updateEmail(UpdateEmailReqDto reqDto, LoggedUser loggedUser);

    SimpleMessageResDto updatePassword(UpdatePasswordReqDto reqDto, LoggedUser loggedUser);

    SimpleMessageResDto delete(DeleteAccountReqDto reqDto, LoggedUser loggedUser);
}
