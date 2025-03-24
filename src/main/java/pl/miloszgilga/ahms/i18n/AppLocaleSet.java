package pl.miloszgilga.ahms.i18n;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AppLocaleSet implements LocaleEnumSet {
    // respones
    REGISTERED_RES("ahms.message.RegisteredRes"),
    LOGOUT_RES("ahms.message.LogoutRes"),
    ACTIVATED_ACCOUNT_RES("ahms.message.ActivateAccountRes"),
    RESET_PASSWORD_REQUEST_RES("ahms.message.ResetPasswordRequestRes"),
    RESET_PASSWORD_CHANGE_RES("ahms.message.ResetPasswordChangeRes"),
    NEW_NAME_SET_RES("ahms.message.NewNameSetRes"),
    NEW_LOGIN_SET_RES("ahms.message.NewLoginSetRes"),
    NEW_EMAIL_ADDRESS_SET_RES("ahms.message.NewEmailAddressSetRes"),
    NEW_PASSWORD_SET_RES("ahms.message.NewPasswordSetRes"),
    REMOVE_ACCOUNT_RES("ahms.message.RemoveAccountRes"),
    UPDATE_USER_LEVEL_RES("ahms.message.UpdateUserLevelRes"),
    UPDATE_USER_EXP_RES("ahms.message.UpdateUserExpRes"),
    UPDATE_USER_MONEY_RES("ahms.message.UpdateUserMoneyRes"),
    BOUGHT_PLANE_RES("ahms.message.BougthPlaneRes"),
    BOUGHT_WORKER_RES("ahms.message.BougthWorkerRes"),
    ADD_NEW_CREW_RES("ahms.message.AddNewCrewRes"),
    SEND_PLANE_ON_ROUTE_RES("ahms.message.SendPlaneOnRouteRes"),
    REVERT_PLANE_ON_ROUTE_RES("ahms.message.RevertPlaneOnRouteRes"),
    // email
    ACTIVATED_ACCOUNT_TITLE_MAIL("ahms.email.title.ActivatedAccount"),
    REGISTER_TITLE_MAIL("ahms.email.title.Register"),
    REQUEST_CHANGE_PASSWORD_TITLE_MAIL("ahms.email.title.RequestChangePassword"),
    CHANGE_PASSWORD_TITLE_MAIL("ahms.email.title.ChangePassword"),
    DELETE_ACCOUNT_TITLE_MAIL("ahms.email.title.DeletedAccount"),
    // exceptions
    SECURITY_AUTHENTICATION_EXC("ahms.exception.AuthenticationException"),
    NO_HANDLER_FOUND_EXC("ahms.exception.NoHandlerFoundException"),
    HTTP_MESSAGE_NOT_READABLE_EXC("ahms.exception.HttpMessageNotReadableException"),
    UNABLE_TO_SEND_EMAIL_EXC("ahms.exception.UnableToSendEmailException"),
    INTERNAL_SERVER_ERROR_EXC("ahms.exception.InternalServerError"),
    USER_NOT_FOUND_EXC("ahms.exception.UserNotFoundExc"),
    INCORRECT_JWT_EXC("ahms.exception.IncorrectJwtExc"),
    REFRESH_TOKEN_NOT_FOUND_EXC("ahms.exception.RefreshTokenNotFoundExc"),
    OTA_TOKEN_NOT_FOUND_EXC("ahms.exception.OtaTokenNotFoundExc"),
    ACCOUNT_HAS_BEEN_ALREADY_ACTIVATED_EXC("ahms.exception.AccountHasBeenAlreadyActivatedExc"),
    JWT_IS_NOT_RELATED_WITH_REFRESH_TOKEN_EXC("ahms.exception.JwtIsNotRelatedWithRefreshTokenExc"),
    LOGIN_ALREADY_EXIST_EXC("ahms.exception.LoginAlreadyExistExc"),
    EMAIL_ADDRESS_ALREADY_EXIST_EXC("ahms.exception.EmailAddressAlreadyExistExc"),
    CATEGORY_TYPE_NOT_EXIST_EXC("ahms.exception.CategoryTypeNotExistExc"),
    PLANE_NOT_EXIST_EXC("ahms.exception.PlaneNotExistExc"),
    WORKER_NOT_EXIST_EXC("ahms.exception.WorkerNotExistExc"),
    PLANE_NOT_EXIST_OR_NOT_BOUGHT_EXC("ahms.exception.PlaneNotExistOrNotBoughtExc"),
    WORKER_NOT_EXIST_OR_NOT_BOUGHT_EXC("ahms.exception.WorkerNotExistOrNotBoughtExc"),
    NOT_ENOUGHT_ROUTES_EXC("ahms.exception.NotEnoughtRoutesExc"),
    LOCKED_PLANE_EXC("ahms.exception.LockedPlaneExc"),
    NON_EXISTING_TEMP_STATS_EXC("ahms.exception.NonExistingTempStatsExc"),
    ROUTE_NOT_FOUND_EXC("ahms.exception.RouteNotFoundExc"),
    WORKER_IN_SHOP_NOT_EXIST_EXC("ahms.exception.WorkerInShopNotExistExc"),
    ACCOUNT_HAS_NOT_ENOUGHT_MONEY_EXC("ahms.exception.AccountHasNotEnoughtMoneyExc"),
    // spring security
    USER_CREDENTIALS_INVALID_EXC("AbstractUserDetailsAuthenticationProvider.badCredentials");

    private final String holder;
}
