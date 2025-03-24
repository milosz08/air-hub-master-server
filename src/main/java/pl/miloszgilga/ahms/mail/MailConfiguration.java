package pl.miloszgilga.ahms.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

@Configuration
@RequiredArgsConstructor
class MailConfiguration {
    private final MailProperties mailProperties;

    @Bean
    FreeMarkerConfigurationFactoryBean freemarkerConfiguration() {
        final FreeMarkerConfigurationFactoryBean bean = new FreeMarkerConfigurationFactoryBean();
        bean.setTemplateLoaderPath(mailProperties.getTemplatesDir());
        return bean;
    }
}
