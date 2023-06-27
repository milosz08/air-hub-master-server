/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: GameService.java
 * Last modified: 6/26/23, 4:06 AM
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

package pl.miloszgilga.network.game;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.time.ZonedDateTime;

import org.jmpsl.security.user.AuthUser;
import org.jmpsl.core.i18n.LocaleMessageService;
import org.jmpsl.core.db.AbstractAuditableEntity;

import pl.miloszgilga.i18n.AppLocaleSet;
import pl.miloszgilga.security.SecurityUtils;
import pl.miloszgilga.dto.SimpleMessageResDto;
import pl.miloszgilga.algorithms.BoostLevelDto;
import pl.miloszgilga.algorithms.GameAlgorithms;
import pl.miloszgilga.network.game.dto.RouteDto;
import pl.miloszgilga.network.game.dto.CrewInFlightDto;
import pl.miloszgilga.network.game.dto.PlaneWithRoutesDto;
import pl.miloszgilga.network.game.dto.PlaneWithWorkersAndRouteDto;
import pl.miloszgilga.network.game.reqdto.GenerateStatsReqDto;
import pl.miloszgilga.network.game.resdto.*;

import pl.miloszgilga.domain.user.UserEntity;
import pl.miloszgilga.domain.user.IUserRepository;
import pl.miloszgilga.domain.plane.PlaneEntity;
import pl.miloszgilga.domain.plane_route.PlaneRouteEntity;
import pl.miloszgilga.domain.plane_route.IPlaneRouteRepository;
import pl.miloszgilga.domain.temp_stats.TempStatsEntity;
import pl.miloszgilga.domain.temp_stats.ITempStatsRepository;
import pl.miloszgilga.domain.in_game_crew.InGameCrewEntity;
import pl.miloszgilga.domain.in_game_plane_params.InGamePlaneParamEntity;
import pl.miloszgilga.domain.in_game_plane_params.IInGamePlaneParamRepository;
import pl.miloszgilga.domain.in_game_worker_params.InGameWorkerParamEntity;

import static pl.miloszgilga.exception.GameException.LockedPlaneException;
import static pl.miloszgilga.exception.GameException.RouteNotFoundException;
import static pl.miloszgilga.exception.GameException.PlaneNotExistException;
import static pl.miloszgilga.exception.GameException.NotEnoughtRoutesException;
import static pl.miloszgilga.exception.GameException.NonExistingTempStatsException;
import static pl.miloszgilga.exception.ShopException.AccountHasNotEnoughtMoneyException;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

@Slf4j
@Service
@RequiredArgsConstructor
public class GameService implements IGameService {

    private final SecurityUtils securityUtils;
    private final GameAlgorithms gameAlgorithms;
    private final LocaleMessageService messageService;

    private final IUserRepository userRepository;
    private final ITempStatsRepository tempStatsRepository;
    private final IPlaneRouteRepository planeRouteRepository;
    private final IInGamePlaneParamRepository inGamePlaneParamRepository;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public PlanesWithRoutesResDto getPlanesWithRoutes(AuthUser user) {
        final UserEntity userEntity = securityUtils.getLoggedUser(user);

        final List<PlaneWithRoutesDto> planeWithRoutesDtos = new ArrayList<>();
        final List<InGamePlaneParamEntity> planes = inGamePlaneParamRepository.findAllInJoiningTable(userEntity.getId());

        for (final InGamePlaneParamEntity plane : planes) {
            final List<PlaneRouteEntity> routes = planeRouteRepository.findAllByPlane_Id(plane.getId());
            if (routes.size() != 3) {
                throw new NotEnoughtRoutesException(plane);
            }
            final PlaneEntity planeEntity = plane.getPlane();
            final List<RouteDto> routeDtos = new ArrayList<>();

            final Set<Integer> randomValues = new HashSet<>();
            while (randomValues.size() < 3) {
                int randomNumber = RandomUtils.nextInt(1, planeEntity.getMaxHours() + 1);
                randomValues.add(randomNumber);
            }
            int i = 0;
            for (final PlaneRouteEntity routeEntity : routes) {
                if (userEntity.getRoutesBlocked() && !Objects.isNull(routeEntity.getRouteHours())) {
                    routeDtos.add(new RouteDto(routeEntity.getId(), routeEntity.getRouteHours()));
                } else {
                    final int generatedHours = new ArrayList<>(randomValues).get(i++);
                    routeEntity.setRouteHours(generatedHours);
                    routeDtos.add(new RouteDto(routeEntity.getId(), generatedHours));
                    log.info("Successfully generated new route [{}] hours: {}", routeEntity, generatedHours);
                }
            }
            planeWithRoutesDtos.add(PlaneWithRoutesDto.builder()
                .planeId(plane.getId())
                .planeName(planeEntity.getName())
                .planeCategory(planeEntity.getCategory().getName())
                .level(plane.getUpgrade())
                .routes(routeDtos)
                .crew(plane.getCrew().stream().map(AbstractAuditableEntity::getId).toList())
                .build());
        }
        userEntity.setRoutesBlocked(true);
        userRepository.save(userEntity);

        final List<String> parsedCategories = planes.stream()
            .map(p -> p.getPlane().getCategory().getName())
            .distinct()
            .toList();

        return PlanesWithRoutesResDto.builder()
            .planesCategories(parsedCategories)
            .planes(planeWithRoutesDtos)
            .build();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public List<InFlightPlaneResDto> getInFlightPlanes(AuthUser user) {
        final UserEntity userEntity = securityUtils.getLoggedUser(user);
        final List<InFlightPlaneResDto> inFlightPlaneResDtos = new ArrayList<>();

        final List<InGamePlaneParamEntity> planes = inGamePlaneParamRepository
            .findAllByUser_IdAndAvailableIsNotNull(userEntity.getId());

        for (final InGamePlaneParamEntity plane : planes) {
            final List<CrewInFlightDto> workers = plane.getCrew().stream()
                .map(c -> new CrewInFlightDto(c.getWorker())).toList();
            inFlightPlaneResDtos.add(new InFlightPlaneResDto(plane, workers));
        }
        return inFlightPlaneResDtos;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    @Transactional
    public GeneratedSendPlaneStatsResDto generateStats(GenerateStatsReqDto reqDto, AuthUser user) {
        final UserEntity userEntity = securityUtils.getLoggedUser(user);
        final PlaneWithWorkersAndRouteDto details = getPlaneDetails(userEntity, reqDto.getPlaneId(), reqDto.getRouteId());
        if (Objects.isNull(details.route().getRouteHours())) {
            throw new RouteNotFoundException(details.route().getId());
        }
        final ZonedDateTime arrival = ZonedDateTime.now().plusHours(details.route().getRouteHours());
        final int exp = gameAlgorithms.generateExp(details, userEntity.getLevel());
        final BoostLevelDto boostLevelDto = gameAlgorithms.checkIfBoostLevel(exp, userEntity);
        final int prize = gameAlgorithms.generatePrize(details, userEntity.getLevel());
        final int flightCosts = gameAlgorithms.generateFlightCosts(details, prize);

        if (userEntity.getMoney() - flightCosts < 0) {
            throw new AccountHasNotEnoughtMoneyException(userEntity.getMoney(), flightCosts);
        }
        final TempStatsEntity tempStatsEntity = TempStatsEntity.builder()
            .selectedRoute(details.route().getId())
            .arrivalTime(arrival)
            .upgradedLevel(boostLevelDto.nextLevel())
            .addedExp(exp)
            .flightCosts(flightCosts)
            .build();
        tempStatsEntity.setPlane(details.plane());

        tempStatsRepository.deleteByPlane_IdAndPlane_User_Id(reqDto.getPlaneId(), userEntity.getId());
        tempStatsRepository.save(tempStatsEntity);

        inGamePlaneParamRepository.save(details.plane());

        log.info("Successfully generated stats for plane with id: {} on route with id: {}",
            reqDto.getPlaneId(), reqDto.getRouteId());

        return GeneratedSendPlaneStatsResDto.builder()
            .currentLevel(userEntity.getLevel())
            .fromLevel(boostLevelDto.fromLevel())
            .toLevel(boostLevelDto.toLevel())
            .nextLevel(boostLevelDto.nextLevel())
            .maxLevels(gameAlgorithms.getMaxLevel())
            .isUpgraded(boostLevelDto.isBoosted())
            .addedExp(exp)
            .prize(prize)
            .totalCost(flightCosts)
            .accountDeposit(Math.max(userEntity.getMoney() - flightCosts, 0))
            .arrival(arrival)
            .build();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    @Transactional
    public SimpleMessageResDto sendPlane(Long planeId, AuthUser user) {
        final UserEntity userEntity = securityUtils.getLoggedUser(user);
        final TempStatsEntity tempStats = tempStatsRepository
            .findByPlane_IdAndPlane_User_Id(planeId, userEntity.getId())
            .orElseThrow(() -> new NonExistingTempStatsException(planeId));

        final PlaneWithWorkersAndRouteDto details = getPlaneDetails(userEntity, planeId, tempStats.getSelectedRoute());
        for (final InGameWorkerParamEntity workerParam : details.workers()) {
            workerParam.setAvailable(tempStats.getArrivalTime());
        }
        details.plane().setAvailable(tempStats.getArrivalTime());
        gameAlgorithms.generateDamage(details.plane());

        userEntity.setLevel(tempStats.getUpgradedLevel());
        userEntity.setExp(userEntity.getExp() + tempStats.getAddedExp());
        userEntity.setMoney(userEntity.getMoney() - tempStats.getFlightCosts());

        userRepository.save(userEntity);
        tempStatsRepository.delete(tempStats);

        log.info("Successfully send plane with id: {} on route with id: {}", planeId, tempStats.getSelectedRoute());
        return new SimpleMessageResDto(messageService.getMessage(AppLocaleSet.SEND_PLANE_ON_ROUTE_RES));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public SimpleMessageResDto revertPlane(Long planeId, AuthUser user) {
        final UserEntity userEntity = securityUtils.getLoggedUser(user);
        tempStatsRepository.deleteByPlane_IdAndPlane_User_Id(planeId, userEntity.getId());

        log.info("Successfully delete temp stats with plane: {}", planeId);
        return new SimpleMessageResDto(messageService.getMessage(AppLocaleSet.REVERT_PLANE_ON_ROUTE_RES));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private PlaneWithWorkersAndRouteDto getPlaneDetails(UserEntity user, Long planeId, Long routeId) {
        final InGamePlaneParamEntity plane = inGamePlaneParamRepository
            .findByPlaneIdInJoiningTable(user.getId(), planeId)
            .orElseThrow(() -> new PlaneNotExistException(planeId));
        if (!Objects.isNull(plane.getAvailable())) {
            throw new LockedPlaneException(planeId);
        }
        final PlaneRouteEntity planeRouteEntity = plane.getPlaneRouteEntities().stream()
            .filter(r -> r.getId().equals(routeId))
            .findFirst()
            .orElseThrow(() -> new RouteNotFoundException(routeId));

        final List<InGameWorkerParamEntity> workers = plane.getCrew().stream()
            .map(InGameCrewEntity::getWorker).toList();

        return PlaneWithWorkersAndRouteDto.builder()
            .plane(plane)
            .route(planeRouteEntity)
            .workers(workers)
            .build();
    }
}
