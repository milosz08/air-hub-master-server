/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.network.user_stats;

import org.jmpsl.security.user.AuthUser;
import pl.miloszgilga.network.user_stats.reqdto.UpdateExpReqDto;
import pl.miloszgilga.network.user_stats.reqdto.UpdateLevelReqDto;
import pl.miloszgilga.network.user_stats.reqdto.UpdateMoneyReqDto;
import pl.miloszgilga.network.user_stats.resdto.UpdateExpResDto;
import pl.miloszgilga.network.user_stats.resdto.UpdateLevelResDto;
import pl.miloszgilga.network.user_stats.resdto.UpdateMoneyResDto;

interface UserStatsService {
    UpdateLevelResDto level(UpdateLevelReqDto reqDto, AuthUser user);
    UpdateExpResDto exp(UpdateExpReqDto reqDto, AuthUser user);
    UpdateMoneyResDto money(UpdateMoneyReqDto reqDto, AuthUser user);
}
