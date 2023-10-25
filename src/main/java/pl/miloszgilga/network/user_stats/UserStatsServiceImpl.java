/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.network.user_stats;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jmpsl.core.i18n.LocaleMessageService;
import org.jmpsl.security.user.AuthUser;
import org.springframework.stereotype.Service;
import pl.miloszgilga.domain.user.UserEntity;
import pl.miloszgilga.domain.user.UserRepository;
import pl.miloszgilga.i18n.AppLocaleSet;
import pl.miloszgilga.network.user_stats.reqdto.UpdateExpReqDto;
import pl.miloszgilga.network.user_stats.reqdto.UpdateLevelReqDto;
import pl.miloszgilga.network.user_stats.reqdto.UpdateMoneyReqDto;
import pl.miloszgilga.network.user_stats.resdto.UpdateExpResDto;
import pl.miloszgilga.network.user_stats.resdto.UpdateLevelResDto;
import pl.miloszgilga.network.user_stats.resdto.UpdateMoneyResDto;
import pl.miloszgilga.security.SecurityUtils;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
class UserStatsServiceImpl implements UserStatsService {
    private final SecurityUtils securityUtils;
    private final UserRepository userRepository;
    private final LocaleMessageService messageService;

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
