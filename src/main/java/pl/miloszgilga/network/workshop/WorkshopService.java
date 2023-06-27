/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: WorkshopService.java
 * Last modified: 6/27/23, 3:06 AM
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

package pl.miloszgilga.network.workshop;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

import org.jmpsl.security.user.AuthUser;

import pl.miloszgilga.security.SecurityUtils;
import pl.miloszgilga.network.workshop.resdto.WorkshopPlaneStatsResDto;

import pl.miloszgilga.domain.user.UserEntity;
import pl.miloszgilga.domain.in_game_plane_params.IInGamePlaneParamRepository;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkshopService implements IWorkshopService {

    private final SecurityUtils securityUtils;
    private final IInGamePlaneParamRepository inGamePlaneParamRepository;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public List<WorkshopPlaneStatsResDto> getPlanes(AuthUser user) {
        final UserEntity userEntity = securityUtils.getLoggedUser(user);
        return inGamePlaneParamRepository.findAllByUser_IdAndAvailableIsNull(userEntity.getId()).stream()
            .map(WorkshopPlaneStatsResDto::new)
            .toList();
    }
}
