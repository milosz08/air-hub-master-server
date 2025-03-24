package pl.miloszgilga.ahms.mail;

import freemarker.template.SimpleScalar;
import freemarker.template.SimpleSequence;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

@RequiredArgsConstructor
class MessageResolverMethod implements TemplateMethodModelEx {
    private final Locale locale;
    private final MessageSource messageSource;

    @Override
    public Object exec(List arguments) throws TemplateModelException {
        if (arguments.isEmpty() || arguments.size() > 2) {
            throw new TemplateModelException("Wrong number of arguments");
        }
        final String code = ((SimpleScalar) arguments.get(0)).getAsString();
        if (Objects.isNull(code) || code.isEmpty()) {
            throw new TemplateModelException("Invalid code value '" + code + "'");
        }
        if (arguments.size() == 2) {
            final SimpleSequence args = ((SimpleSequence) arguments.get(1));
            final String[] argsParsed = new String[args.size()];
            for (int i = 0; i < args.size(); i++) {
                argsParsed[i] = ((SimpleScalar) args.get(i)).getAsString();
            }
            return messageSource.getMessage(code, argsParsed, locale);
        }
        return messageSource.getMessage(code, null, locale);
    }
}
