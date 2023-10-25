/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.exception;

import lombok.extern.slf4j.Slf4j;
import org.jmpsl.core.exception.RestServiceServerException;
import org.springframework.http.HttpStatus;
import pl.miloszgilga.i18n.AppLocaleSet;

import java.util.Map;

public class CategoryException {
    @Slf4j
    public static class CategoryTypeNotExistException extends RestServiceServerException {
        public CategoryTypeNotExistException(String nonExistingType) {
            super(HttpStatus.NOT_FOUND, AppLocaleSet.CATEGORY_TYPE_NOT_EXIST_EXC, Map.of(
                "type", nonExistingType
            ));
            log.error("Attemt to get category list with non existing type: {}", nonExistingType);
        }
    }
}
