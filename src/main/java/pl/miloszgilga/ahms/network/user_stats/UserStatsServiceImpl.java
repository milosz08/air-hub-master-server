package pl.miloszgilga.ahms.network.user_stats;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.miloszgilga.ahms.domain.user.UserEntity;
import pl.miloszgilga.ahms.domain.user.UserRepository;
import pl.miloszgilga.ahms.i18n.AppLocaleSet;
import pl.miloszgilga.ahms.i18n.LocaleMessageService;
import pl.miloszgilga.ahms.network.user_stats.reqdto.UpdateExpReqDto;
import pl.miloszgilga.ahms.network.user_stats.reqdto.UpdateLevelReqDto;
import pl.miloszgilga.ahms.network.user_stats.reqdto.UpdateMoneyReqDto;
import pl.miloszgilga.ahms.network.user_stats.resdto.UpdateExpResDto;
import pl.miloszgilga.ahms.network.user_stats.resdto.UpdateLevelResDto;
import pl.miloszgilga.ahms.network.user_stats.resdto.UpdateMoneyResDto;
import pl.miloszgilga.ahms.security.LoggedUser;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
class UserStatsServiceImpl implements UserStatsService {
    private final UserRepository userRepository;
    private final LocaleMessageService localeMessageService;

    @Override
    public UpdateLevelResDto level(UpdateLevelReqDto reqDto, LoggedUser loggedUser) {
        final UserEntity userEntity = loggedUser.userEntity();
        final Byte prevValue = userEntity.getLevel();

        userEntity.setLevel((byte) (reqDto.getLevel().intValue()));
        userRepository.save(userEntity);

        log.info("User level has been successfully updated from '{}' to '{}' for {}", prevValue, reqDto.getLevel(),
            userEntity.getLogin());
        return new UpdateLevelResDto(localeMessageService.getMessage(AppLocaleSet.UPDATE_USER_LEVEL_RES, Map.of(
            "prev", prevValue,
            "next", reqDto.getLevel()
        )), (byte) reqDto.getLevel().intValue());
    }

    @Override
    public UpdateExpResDto exp(UpdateExpReqDto reqDto, LoggedUser loggedUser) {
        final UserEntity userEntity = loggedUser.userEntity();
        final Integer prevValue = userEntity.getExp();

        userEntity.setExp(reqDto.getExp());
        userRepository.save(userEntity);

        log.info("User experience has been successfully updated from '{}' to '{}' for {}", prevValue, reqDto.getExp(),
            userEntity.getLogin());
        return new UpdateExpResDto(localeMessageService.getMessage(AppLocaleSet.UPDATE_USER_EXP_RES, Map.of(
            "prev", prevValue,
            "next", reqDto.getExp()
        )), reqDto.getExp());
    }

    @Override
    public UpdateMoneyResDto money(UpdateMoneyReqDto reqDto, LoggedUser loggedUser) {
        final UserEntity userEntity = loggedUser.userEntity();
        final Long prevValue = userEntity.getMoney();

        userEntity.setMoney(reqDto.getMoney());
        userRepository.save(userEntity);

        log.info("User money has been successfully updated from '{}' to '{}' for {}", prevValue, reqDto.getMoney(),
            userEntity.getLogin());
        return new UpdateMoneyResDto(localeMessageService.getMessage(AppLocaleSet.UPDATE_USER_MONEY_RES, Map.of(
            "prev", prevValue,
            "next", reqDto.getMoney()
        )), reqDto.getMoney());
    }
}
