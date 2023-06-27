/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: CrewService.java
 * Last modified: 6/27/23, 2:35 AM
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

package pl.miloszgilga.network.crew;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import org.jmpsl.security.user.AuthUser;
import org.jmpsl.core.i18n.LocaleMessageService;

import pl.miloszgilga.utils.Utilities;
import pl.miloszgilga.i18n.AppLocaleSet;
import pl.miloszgilga.security.SecurityUtils;
import pl.miloszgilga.dto.SimpleMessageResDto;
import pl.miloszgilga.network.crew.reqdto.AddCrewReqDto;
import pl.miloszgilga.network.crew.resdto.CrewDataResDto;
import pl.miloszgilga.network.crew.dto.CrewPlaneDto;
import pl.miloszgilga.network.crew.dto.CrewWorkerDto;

import pl.miloszgilga.domain.user.UserEntity;
import pl.miloszgilga.domain.in_game_crew.InGameCrewEntity;
import pl.miloszgilga.domain.in_game_plane_params.InGamePlaneParamEntity;
import pl.miloszgilga.domain.in_game_plane_params.IInGamePlaneParamRepository;
import pl.miloszgilga.domain.in_game_worker_params.InGameWorkerParamEntity;
import pl.miloszgilga.domain.in_game_worker_params.IInGameWorkerParamRepository;

import pl.miloszgilga.exception.CrewException.PlaneNotExistOrNotBoughtException;
import pl.miloszgilga.exception.CrewException.WorkerNotExistOrNotBoughtException;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

@Slf4j
@Service
@RequiredArgsConstructor
public class CrewService implements ICrewService {

    private final SecurityUtils securityUtils;
    private final LocaleMessageService messageService;

    private final IInGamePlaneParamRepository inGamePlaneParamRepository;
    private final IInGameWorkerParamRepository inGameWorkerParamRepository;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public CrewDataResDto getCrew(AuthUser user) {
        final UserEntity userEntity = securityUtils.getLoggedUser(user);

        final List<InGamePlaneParamEntity> planes = inGamePlaneParamRepository.findAllExcInJoiningTable(userEntity.getId());
        final List<InGameWorkerParamEntity> workers = inGameWorkerParamRepository.findAllExcInJoiningTable(userEntity.getId());

        final List<CrewPlaneDto> planeDtos = planes.stream()
            .map(CrewPlaneDto::new)
            .toList();

        final Map<String, List<CrewWorkerDto>> crewWorkerDtos = new HashMap<>();
        for (final InGameWorkerParamEntity worker : workers) {
            final String categoryName = worker.getWorker().getCategory().getName();

            final List<CrewWorkerDto> entities = crewWorkerDtos.get(categoryName);
            if (Objects.isNull(entities)) {
                final List<CrewWorkerDto> crewDtos = new ArrayList<>();
                crewDtos.add(new CrewWorkerDto(worker));
                crewWorkerDtos.put(Utilities.parseToCamelCase(categoryName), crewDtos);
            } else {
                entities.add(new CrewWorkerDto(worker));
            }
        }
        return CrewDataResDto.builder()
            .planes(planeDtos)
            .workers(crewWorkerDtos)
            .build();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public SimpleMessageResDto addCrew(AddCrewReqDto reqDto, AuthUser user) {
        final UserEntity userEntity = securityUtils.getLoggedUser(user);

        final InGamePlaneParamEntity plane = inGamePlaneParamRepository
            .findByIdAndUser_Id(reqDto.getPlaneId(), userEntity.getId())
            .orElseThrow(() -> new PlaneNotExistOrNotBoughtException(reqDto.getPlaneId()));

        for (final Long workerId : reqDto.getCrew()) {
            if (!inGameWorkerParamRepository.existsByIdAndUser_Id(workerId, userEntity.getId())) {
                throw new WorkerNotExistOrNotBoughtException(reqDto.getCrew());
            }
        }
        final List<InGameWorkerParamEntity> workers = inGameWorkerParamRepository
            .findAllByIdInAndUser_Id(reqDto.getCrew(), userEntity.getId());

        if (workers.isEmpty()) {
            throw new WorkerNotExistOrNotBoughtException(reqDto.getCrew());
        }
        for (final InGameWorkerParamEntity worker : workers) {
            final InGameCrewEntity crew = new InGameCrewEntity(plane, worker);
            plane.persistInGameCrewEntity(crew);
            crew.setWorker(worker);
        }
        inGamePlaneParamRepository.save(plane);

        final String crewParsed = workers.stream()
            .map(w -> Utilities.parseWorkerFullName(w.getWorker()))
            .collect(Collectors.joining(", "));

        log.info("Crew for plane: {} was successfully added. Crew data: {}", plane.getPlane().getName(), crewParsed);
        return new SimpleMessageResDto(messageService.getMessage(AppLocaleSet.ADD_NEW_CREW_RES));
    }
}
