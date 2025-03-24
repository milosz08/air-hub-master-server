package pl.miloszgilga.ahms.mail;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "application.mail")
class MailProperties {
    private String templatesDir;
    private String sendEmailAddress;
    private String replyEmailAddress;
}
