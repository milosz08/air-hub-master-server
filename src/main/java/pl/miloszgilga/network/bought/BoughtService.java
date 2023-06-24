/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: BoughtService.java
 * Last modified: 6/24/23, 2:10 AM
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

package pl.miloszgilga.network.bought;

import lombok.RequiredArgsConstructor;

import org.jmpsl.security.user.AuthUser;
import org.springframework.stereotype.Service;

import java.util.List;

import pl.miloszgilga.security.SecurityUtils;
import pl.miloszgilga.network.bought.resdto.InGamePlaneResDto;
import pl.miloszgilga.network.bought.resdto.InGameWorkerResDto;

import pl.miloszgilga.domain.user.UserEntity;
import pl.miloszgilga.domain.in_game_plane_params.IInGamePlaneParamRepository;
import pl.miloszgilga.domain.in_game_worker_params.IInGameWorkerParamRepository;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

@Service
@RequiredArgsConstructor
public class BoughtService implements IBoughtService {

    private final SecurityUtils securityUtils;

    private final IInGamePlaneParamRepository inGamePlaneParamRepository;
    private final IInGameWorkerParamRepository inGameWorkerParamRepository;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public List<InGamePlaneResDto> boughtPlanes(AuthUser user) {
        final UserEntity userEntity = securityUtils.getLoggedUser(user);
        return inGamePlaneParamRepository.findAllByUser_Id(userEntity.getId()).stream()
            .map(InGamePlaneResDto::new)
            .toList();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public List<InGameWorkerResDto> boughtWorkers(AuthUser user) {
        final UserEntity userEntity = securityUtils.getLoggedUser(user);
        return inGameWorkerParamRepository.findAllByUser_Id(userEntity.getId()).stream()
            .map(InGameWorkerResDto::new)
            .toList();
    }
}
