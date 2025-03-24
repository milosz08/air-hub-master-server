package pl.miloszgilga.ahms.network.user_stats;

import pl.miloszgilga.ahms.network.user_stats.reqdto.UpdateExpReqDto;
import pl.miloszgilga.ahms.network.user_stats.reqdto.UpdateLevelReqDto;
import pl.miloszgilga.ahms.network.user_stats.reqdto.UpdateMoneyReqDto;
import pl.miloszgilga.ahms.network.user_stats.resdto.UpdateExpResDto;
import pl.miloszgilga.ahms.network.user_stats.resdto.UpdateLevelResDto;
import pl.miloszgilga.ahms.network.user_stats.resdto.UpdateMoneyResDto;
import pl.miloszgilga.ahms.security.LoggedUser;

interface UserStatsService {
    UpdateLevelResDto level(UpdateLevelReqDto reqDto, LoggedUser loggedUser);

    UpdateExpResDto exp(UpdateExpReqDto reqDto, LoggedUser loggedUser);

    UpdateMoneyResDto money(UpdateMoneyReqDto reqDto, LoggedUser loggedUser);
}
