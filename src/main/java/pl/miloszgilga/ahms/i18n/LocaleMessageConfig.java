package pl.miloszgilga.ahms.i18n;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.LocaleResolver;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;

@Configuration
@RequiredArgsConstructor
class LocaleMessageConfig {
    private final I18nProperties i18nProperties;

    @Bean
    static MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

    @Bean
    MessageSource messageSource() {
        final ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
        final List<String> localeBundlePaths = i18nProperties.getBundles();
        resourceBundleMessageSource.addBasenames(localeBundlePaths.toArray(new String[0]));
        resourceBundleMessageSource.setDefaultEncoding(String.valueOf(StandardCharsets.UTF_8));
        return resourceBundleMessageSource;
    }

    @Bean
    LocaleResolver localeResolver() {
        final Locale defaultLocale = new Locale(i18nProperties.getDefaultLangauge());
        final List<Locale> availableLocales = i18nProperties.getAvailableLanguages().stream()
            .map(Locale::new)
            .toList();
        LocaleContextHolder.setDefaultLocale(defaultLocale);
        final HeaderLocaleResolver customLocaleResolver = new HeaderLocaleResolver(availableLocales);
        customLocaleResolver.setDefaultLocale(defaultLocale);
        customLocaleResolver.setSupportedLocales(availableLocales);
        return customLocaleResolver;
    }
}
