/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: AppScheduler.java
 * Last modified: 19/05/2023, 13:10
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

package pl.miloszgilga.scheduler;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

import pl.miloszgilga.config.ApiProperties;

import pl.miloszgilga.domain.user.IUserRepository;
import pl.miloszgilga.domain.ota_token.IOtaTokenRepository;
import pl.miloszgilga.domain.blacklist_jwt.IBlacklistJwtRepository;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

@Slf4j
@Component
@EnableAsync
@RequiredArgsConstructor
public class AppScheduler {

    private final ApiProperties properties;

    private final IUserRepository userRepository;
    private final IOtaTokenRepository otaTokenRepository;
    private final IBlacklistJwtRepository blacklistJwtRepository;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Async
    @Transactional
    @Scheduled(cron = "0 0 */48 * * *")
    public void removeUnactivatedUserAccounts() {
        userRepository.deleteAllNotActivatedAccount(ZonedDateTime.now().plusHours(properties.getOtaExpiredRegisterHours()));
        log.info("Invoke deleting unactivated account scheduler.");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Async
    @Transactional
    @Scheduled(cron = "0 0 */1 * * *")
    public void removeExpiredBlacklistJwts() {
        blacklistJwtRepository.deleteAllExpiredJwts();
        log.info("Invoke deleting expierd JWTs on blacklist scheduler.");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Async
    @Transactional
    @Scheduled(cron = "0 0 */24 * * *")
    public void removeNonUserOtaTokens() {
        otaTokenRepository.deleteNonUsedOtaTokens();
        log.info("Invoke deleting non used ota tokens scheduler.");
    }
}
