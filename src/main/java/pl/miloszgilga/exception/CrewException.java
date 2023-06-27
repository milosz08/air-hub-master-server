/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: CrewException.java
 * Last modified: 6/27/23, 3:40 AM
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

import java.util.List;

import org.jmpsl.core.exception.RestServiceServerException;

import pl.miloszgilga.i18n.AppLocaleSet;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public class CrewException {

    @Slf4j public static class WorkerNotExistOrNotBoughtException extends RestServiceServerException {
        public WorkerNotExistOrNotBoughtException(List<Long> workerIds) {
            super(HttpStatus.NOT_FOUND, AppLocaleSet.WORKER_NOT_EXIST_OR_NOT_BOUGHT_EXC);
            log.error("Attemt to get wroker by id which does not exist or not bought. Followed ids: {}", workerIds);
        }
    }

    @Slf4j public static class PlaneNotExistOrNotBoughtException extends RestServiceServerException {
        public PlaneNotExistOrNotBoughtException(Long planeId) {
            super(HttpStatus.NOT_FOUND, AppLocaleSet.PLANE_NOT_EXIST_OR_NOT_BOUGHT_EXC);
            log.error("Attemt to get plane by id which does not exist or not bought. Followed id: {}", planeId);
        }
    }
}
