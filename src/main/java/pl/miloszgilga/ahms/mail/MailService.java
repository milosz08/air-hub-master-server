package pl.miloszgilga.ahms.mail;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import pl.miloszgilga.ahms.config.AppProperties;
import pl.miloszgilga.ahms.domain.user.UserEntity;
import pl.miloszgilga.ahms.i18n.AppLocaleSet;
import pl.miloszgilga.ahms.i18n.LocaleMessageService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender javaMailSender;
    private final MessageSource messageSource;
    private final LocaleMessageService localeMessageService;
    private final Configuration freemarkerConfiguration;
    private final MailProperties mailProperties;
    private final AppProperties appProperties;

    public void sendEmail(MailRequestDto reqDto, Map<String, String> model, MailTemplate template) {
        final MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            final MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

            final Template mailTemplate = freemarkerConfiguration.getTemplate(template.getTemplateName(),
                reqDto.getLocale());

            final Map<String, Object> modelWithI18n = new HashMap<>(model);
            modelWithI18n.put("i18n", new MessageResolverMethod(reqDto.getLocale(), messageSource));
            modelWithI18n.put("currentYear", String.valueOf(ZonedDateTime.now().getYear()));
            modelWithI18n.put("serverUtcTime", Instant.now().toString());
            if (!Objects.isNull(reqDto.getRequest())) {
                modelWithI18n.put("baseServletPath", getBaseReqPath(reqDto.getRequest()));
            }
            if (!Objects.isNull(reqDto.getAppName())) {
                modelWithI18n.put("appName", reqDto.getAppName());
            }
            for (final String client : reqDto.getSendTo()) {
                mimeMessageHelper.setTo(client);
            }
            mimeMessageHelper.setText(FreeMarkerTemplateUtils.processTemplateIntoString(mailTemplate, modelWithI18n), true);
            for (final ResourceDto inlineRes : reqDto.getInlineResources()) {
                mimeMessageHelper.addInline(inlineRes.mimeVariableName(), inlineRes.fileHandler());
            }
            for (final ResourceDto attachementRes : reqDto.getAttachments()) {
                mimeMessageHelper.addAttachment(attachementRes.mimeVariableName(), attachementRes.fileHandler());
            }
            mimeMessageHelper.setSubject(reqDto.getMessageSubject());
            mimeMessageHelper.setFrom(reqDto.getSendFrom());
            mimeMessageHelper.setReplyTo(reqDto.getReplyAddress(), reqDto.getAppName());

            javaMailSender.send(mimeMessage);
            log.info("Email message from template {} was successfully send. Request parameters: {}",
                template.getTemplateName(), reqDto);
            return;

        } catch (MessagingException | IOException ex) {
            log.error("Sender mail exception. {}. Request parameters: {}", ex.getMessage(), reqDto);
        } catch (TemplateException ex) {
            log.error("Template exception. {}. Request parameters: {}", ex.getMessage(), reqDto);
        } catch (Exception ex) {
            log.error("Unexpected mail sender exception. {}, Request parameters: {}", ex.getMessage(), reqDto);
        }
        throw new MailException.UnableToSendEmailException(reqDto.getSendTo());
    }

    public MailRequestDto createBaseMailRequest(UserEntity user, AppLocaleSet i18nTitle) {
        return MailRequestDto.builder()
            .sendTo(Set.of(user.getEmailAddress()))
            .sendFrom(mailProperties.getSendEmailAddress())
            .replyAddress(mailProperties.getReplyEmailAddress())
            .messageSubject(localeMessageService.getMessage(i18nTitle, Map.of(
                "appName", appProperties.getAppName(),
                "userLogin", user.getLogin()
            )))
            .appName(appProperties.getAppName())
            .locale(LocaleContextHolder.getLocale())
            .build();
    }

    private String getBaseReqPath(HttpServletRequest req) {
        final boolean isHttp = req.getScheme().equals("http") && req.getServerPort() == 80;
        final boolean isHttps = req.getScheme().equals("https") && req.getServerPort() == 443;
        return req.getScheme() + "://" + req.getServerName() + (isHttp || isHttps ? "" : ":" + req.getServerPort());
    }
}
