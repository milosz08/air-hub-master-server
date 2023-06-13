/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: UserStatsService.java
 * Last modified: 6/13/23, 10:22 PM
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

package pl.miloszgilga.network.user_stats;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.Map;

import org.jmpsl.security.user.AuthUser;
import org.jmpsl.core.i18n.LocaleMessageService;

import pl.miloszgilga.i18n.AppLocaleSet;
import pl.miloszgilga.security.SecurityUtils;

import pl.miloszgilga.network.user_stats.reqdto.UpdateLevelReqDto;
import pl.miloszgilga.network.user_stats.resdto.UpdateLevelResDto;
import pl.miloszgilga.network.user_stats.reqdto.UpdateExpReqDto;
import pl.miloszgilga.network.user_stats.resdto.UpdateExpResDto;
import pl.miloszgilga.network.user_stats.reqdto.UpdateMoneyReqDto;
import pl.miloszgilga.network.user_stats.resdto.UpdateMoneyResDto;

import pl.miloszgilga.domain.user.UserEntity;
import pl.miloszgilga.domain.user.IUserRepository;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

@Slf4j
@Service
@RequiredArgsConstructor
public class UserStatsService implements IUserStatsService {

    private final SecurityUtils securityUtils;
    private final IUserRepository userRepository;
    private final LocaleMessageService messageService;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public UpdateLevelResDto level(UpdateLevelReqDto reqDto, AuthUser user) {
        final UserEntity loggedUser = securityUtils.getLoggedUser(user);
        final Byte prevValue = loggedUser.getLevel();

        loggedUser.setLevel((byte) (reqDto.getLevel().intValue()));
        userRepository.save(loggedUser);

        log.info("User level has been successfully updated from '{}' to '{}' for {}", prevValue, reqDto.getLevel(),
            loggedUser.getLogin());
        return new UpdateLevelResDto(messageService.getMessage(AppLocaleSet.UPDATE_USER_LEVEL_RES, Map.of(
            "prev", prevValue,
            "next", reqDto.getLevel()
        )), (byte) reqDto.getLevel().intValue());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public UpdateExpResDto exp(UpdateExpReqDto reqDto, AuthUser user) {
        final UserEntity loggedUser = securityUtils.getLoggedUser(user);
        final Integer prevValue = loggedUser.getExp();

        loggedUser.setExp(reqDto.getExp());
        userRepository.save(loggedUser);

        log.info("User experience has been successfully updated from '{}' to '{}' for {}", prevValue, reqDto.getExp(),
            loggedUser.getLogin());
        return new UpdateExpResDto(messageService.getMessage(AppLocaleSet.UPDATE_USER_EXP_RES, Map.of(
            "prev", prevValue,
            "next", reqDto.getExp()
        )), reqDto.getExp());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public UpdateMoneyResDto money(UpdateMoneyReqDto reqDto, AuthUser user) {
        final UserEntity loggedUser = securityUtils.getLoggedUser(user);
        final Long prevValue = loggedUser.getMoney();

        loggedUser.setMoney(reqDto.getMoney());
        userRepository.save(loggedUser);

        log.info("User money has been successfully updated from '{}' to '{}' for {}", prevValue, reqDto.getMoney(),
            loggedUser.getLogin());
        return new UpdateMoneyResDto(messageService.getMessage(AppLocaleSet.UPDATE_USER_MONEY_RES, Map.of(
            "prev", prevValue,
            "next", reqDto.getMoney()
        )), reqDto.getMoney());
    }
}
