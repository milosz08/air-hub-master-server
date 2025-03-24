package pl.miloszgilga.ahms.i18n;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "application.i18n")
public class I18nProperties {
    private List<String> availableLanguages;
    private String defaultLangauge;
    private List<String> bundles;
}
