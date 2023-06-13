/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: IUserStatsService.java
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

import org.jmpsl.security.user.AuthUser;

import pl.miloszgilga.network.user_stats.reqdto.UpdateLevelReqDto;
import pl.miloszgilga.network.user_stats.resdto.UpdateLevelResDto;
import pl.miloszgilga.network.user_stats.reqdto.UpdateExpReqDto;
import pl.miloszgilga.network.user_stats.resdto.UpdateExpResDto;
import pl.miloszgilga.network.user_stats.reqdto.UpdateMoneyReqDto;
import pl.miloszgilga.network.user_stats.resdto.UpdateMoneyResDto;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

interface IUserStatsService {
    UpdateLevelResDto level(UpdateLevelReqDto reqDto, AuthUser user);
    UpdateExpResDto exp(UpdateExpReqDto reqDto, AuthUser user);
    UpdateMoneyResDto money(UpdateMoneyReqDto reqDto, AuthUser user);
}
