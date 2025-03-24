package pl.miloszgilga.ahms.mail;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MailTemplate {
    ACTIVATED_ACCOUNT("/activated-account.template.ftl"),
    REGISTER("/register.template.ftl"),
    REQUEST_CHANGE_PASSWORD("/request-change-password.template.ftl"),
    CHANGE_PASSOWRD("/change-password.template.ftl"),
    DELETE_ACCOUNT("/delete-account.template.ftl");

    private final String templateName;
}
