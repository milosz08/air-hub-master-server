/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: CategoryException.java
 * Last modified: 6/14/23, 12:34 AM
 * Project name: air-hub-master-server
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the License at
 *
 *     <http://www.apache.org/license/LICENSE-2.0>
 *
 * Unless required by applicable law or agreed to in writing, software distributed under
 * the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the license.
 */

package pl.miloszgilga.exception;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;

import java.util.Map;

import org.jmpsl.core.exception.RestServiceServerException;

import pl.miloszgilga.i18n.AppLocaleSet;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
