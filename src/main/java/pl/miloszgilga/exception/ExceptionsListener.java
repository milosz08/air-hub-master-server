/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.exception;

import org.jmpsl.core.exception.AbstractBaseRestExceptionListener;
import org.jmpsl.core.i18n.LocaleMessageService;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class ExceptionsListener extends AbstractBaseRestExceptionListener {
    ExceptionsListener(LocaleMessageService messageService) {
        super(messageService);
    }
}
