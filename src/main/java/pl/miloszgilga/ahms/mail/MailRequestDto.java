package pl.miloszgilga.ahms.mail;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.*;

@Getter
@Setter
@Builder
@ToString
public class MailRequestDto {
    private Set<String> sendTo;
    private String sendFrom;
    private String messageSubject;
    private List<ResourceDto> inlineResources;
    private List<ResourceDto> attachments;
    private Locale locale;
    private HttpServletRequest request;
    private String appName;
    private String replyAddress;

    public MailRequestDto(
        Set<String> sendTo, String sendFrom, String messageSubject, List<ResourceDto> inlineResources,
        List<ResourceDto> attachments, Locale locale, HttpServletRequest request, String appName, String replyAddress
    ) {
        this.sendTo = sendTo;
        this.sendFrom = sendFrom;
        this.messageSubject = messageSubject;
        this.inlineResources = Objects.isNull(inlineResources) ? new ArrayList<>() : inlineResources;
        this.attachments = Objects.isNull(attachments) ? new ArrayList<>() : attachments;
        this.locale = locale;
        this.request = request;
        this.appName = appName;
        this.replyAddress = replyAddress;
    }
}
