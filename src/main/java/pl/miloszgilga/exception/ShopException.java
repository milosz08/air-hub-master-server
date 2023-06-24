/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: ShopException.java
 * Last modified: 6/23/23, 3:20 AM
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

import pl.miloszgilga.i18n.AppLocaleSet;

import org.jmpsl.core.exception.RestServiceServerException;

public class ShopException {

    @Slf4j public static class PlaneNotExistException extends RestServiceServerException {
        public PlaneNotExistException(Long nonExistingPlaneId) {
            super(HttpStatus.NOT_FOUND, AppLocaleSet.PLANE_NOT_EXIST_EXC);
            log.error("Attemt to get plane by id which does not exist. Followed id: {}", nonExistingPlaneId);
        }
    }

    @Slf4j public static class WorkerNotExistException extends RestServiceServerException {
        public WorkerNotExistException(Long nonExistingWorkerId) {
            super(HttpStatus.NOT_FOUND, AppLocaleSet.WORKER_NOT_EXIST_EXC);
            log.error("Attemt to get worker by id which does not exist. Followed id: {}", nonExistingWorkerId);
        }
    }

    @Slf4j public static class WorkerInShopNotExistException extends RestServiceServerException {
        public WorkerInShopNotExistException(Long wId, Long uId) {
            super(HttpStatus.NOT_FOUND, AppLocaleSet.WORKER_IN_SHOP_NOT_EXIST_EXC);
            log.error("Attemt to get worker by id which does not exist in shop. Followed id: {}, user id: {}", wId, uId);
        }
    }

    @Slf4j public static class AccountHasNotEnoughtMoneyException extends RestServiceServerException {
        public AccountHasNotEnoughtMoneyException(long onAccount, int price) {
            super(HttpStatus.BAD_REQUEST, AppLocaleSet.ACCOUNT_HAS_NOT_ENOUGHT_MONEY_EXC);
            log.error("Attempt to buy item without enought money on account. On account: {}, item cost: {}", onAccount, price);
        }
    }
}
