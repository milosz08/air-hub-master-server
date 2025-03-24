package pl.miloszgilga.ahms.i18n;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.List;
import java.util.Locale;

@RequiredArgsConstructor
class HeaderLocaleResolver extends AcceptHeaderLocaleResolver {
    private final List<Locale> supportedLocales;

    @Override
    public Locale resolveLocale(HttpServletRequest req) {
        final String acceptLang = req.getHeader(HttpHeaders.ACCEPT_LANGUAGE);
        if (acceptLang.isEmpty()) {
            return Locale.getDefault();
        }
        final List<Locale.LanguageRange> localeRangeList = Locale.LanguageRange.parse(acceptLang);
        final Locale currentLocale = Locale.lookup(localeRangeList, supportedLocales);
        LocaleContextHolder.setLocale(currentLocale);
        return currentLocale;
    }
}
