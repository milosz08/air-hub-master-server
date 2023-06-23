/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: ShopService.java
 * Last modified: 6/23/23, 12:00 AM
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

package pl.miloszgilga.network.shop;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import org.jmpsl.security.user.AuthUser;
import org.jmpsl.core.i18n.LocaleMessageService;

import pl.miloszgilga.utils.Utilities;
import pl.miloszgilga.i18n.AppLocaleSet;
import pl.miloszgilga.security.SecurityUtils;
import pl.miloszgilga.network.shop.resdto.ShopPlanesResDto;
import pl.miloszgilga.network.shop.resdto.ShopWorkersResDto;
import pl.miloszgilga.network.shop.resdto.TransactMoneyStatusResDto;

import pl.miloszgilga.domain.user.UserEntity;
import pl.miloszgilga.domain.user.IUserRepository;
import pl.miloszgilga.domain.worker.WorkerEntity;
import pl.miloszgilga.domain.worker.IWorkerRepository;
import pl.miloszgilga.domain.plane.PlaneEntity;
import pl.miloszgilga.domain.plane.IPlaneRepository;
import pl.miloszgilga.domain.workers_shop.WorkerShopEntity;
import pl.miloszgilga.domain.workers_shop.IWorkerShopRepository;
import pl.miloszgilga.domain.in_game_plane_params.InGamePlaneParamEntity;
import pl.miloszgilga.domain.in_game_worker_params.InGameWorkerParamEntity;

import pl.miloszgilga.exception.ShopException.PlaneNotExistException;
import pl.miloszgilga.exception.ShopException.WorkerNotExistException;
import pl.miloszgilga.exception.ShopException.WorkerInShopNotExistException;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

@Slf4j
@Service
@RequiredArgsConstructor
public class ShopService implements IShopService {

    private final SecurityUtils securityUtils;
    private final LocaleMessageService messageService;

    private final IUserRepository userRepository;
    private final IPlaneRepository planeRepository;
    private final IWorkerRepository workerRepository;
    private final IWorkerShopRepository workerShopRepository;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public List<ShopPlanesResDto> planes(AuthUser user) {
        final UserEntity userEntity = securityUtils.getLoggedUser(user);
        final List<PlaneEntity> planeEntities = planeRepository.findAllLazillyLoadedPlanes();
        return planeEntities.stream()
            .filter(p -> userEntity.getLevel() >= p.getCategory().getLevel())
            .map(p -> new ShopPlanesResDto(p.getId(), p.getName(), p.getCategory().getName()))
            .toList();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public List<ShopWorkersResDto> workers(AuthUser user) {
        final UserEntity userEntity = securityUtils.getLoggedUser(user);
        final List<WorkerEntity> workerEntities = workerRepository.findAllLazillyLoadedWorkers();

        final List<WorkerShopEntity> alreadyExisting = workerShopRepository.findByUseId(userEntity.getId());
        final List<ShopWorkersResDto> shopWorkersResDtos = new ArrayList<>();

        for (final WorkerEntity worker : workerEntities) {

            // TODO: Dodanie algorytmów

            final AtomicInteger exp = new AtomicInteger(RandomUtils.nextInt(0, 101));
            final AtomicInteger coop = new AtomicInteger(RandomUtils.nextInt(0, 101));
            final AtomicInteger reb = new AtomicInteger(RandomUtils.nextInt(0, 101));

            alreadyExisting.stream()
                .filter(s -> s.getWorker().getId().equals(worker.getId()))
                .findFirst()
                .ifPresentOrElse(workerShopEntity -> {
                    if (!userEntity.getWorkersBlocked()) {
                        workerShopEntity.setAbilities(exp.get(), coop.get(), reb.get());
                    } else {
                        exp.set(workerShopEntity.getExperience());
                        coop.set(workerShopEntity.getCooperation());
                        reb.set(workerShopEntity.getRebelliousness());
                    }
                }, () -> {
                    final WorkerShopEntity workerShopEntity = new WorkerShopEntity(exp.get(), coop.get(), reb.get());

                    workerShopEntity.setWorker(worker);
                    userEntity.setWorkersBlocked(true);
                    userEntity.persistWorkerShopEntity(workerShopEntity);
                });
            shopWorkersResDtos.add(new ShopWorkersResDto(worker.getId(), Utilities.parseWorkerFullName(worker),
                worker.getCategory().getName(), exp.get(), coop.get(), reb.get()));
        }
        userRepository.save(userEntity);
        return shopWorkersResDtos;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public TransactMoneyStatusResDto buyPlane(Long planeId, AuthUser user) {
        final UserEntity userEntity = securityUtils.getLoggedUser(user);

        final PlaneEntity boughtPlane = planeRepository.findById(planeId)
            .orElseThrow(() -> new PlaneNotExistException(planeId));

        final InGamePlaneParamEntity inGamePlaneParamEntity = new InGamePlaneParamEntity();

        inGamePlaneParamEntity.setPlane(boughtPlane);
        userEntity.persistInGamePlainParamEntity(inGamePlaneParamEntity);

        userRepository.save(userEntity);

        log.info("Successfully persisted bought plane '{}' for user '{}'", boughtPlane.getName(), userEntity.getLogin());
        return new TransactMoneyStatusResDto(0L, messageService.getMessage(AppLocaleSet.BOUGHT_PLANE_RES, Map.of(
            "planeName", boughtPlane.getName()
        )));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public TransactMoneyStatusResDto buyWorker(Long workerId, AuthUser user) {
        final UserEntity userEntity = securityUtils.getLoggedUser(user);

        final WorkerEntity boughtWorker = workerRepository.findById(workerId)
            .orElseThrow(() -> new WorkerNotExistException(workerId));

        final Long uId = userEntity.getId();
        final String fullName = Utilities.parseWorkerFullName(boughtWorker);

        final WorkerShopEntity workerShopEntity = workerShopRepository.findByWorkerIdAndUserId(workerId, uId)
            .orElseThrow(() -> new WorkerInShopNotExistException(workerId, uId));

        final InGameWorkerParamEntity inGameWorkerParamEntity = new InGameWorkerParamEntity(workerShopEntity);

        inGameWorkerParamEntity.setWorker(boughtWorker);
        userEntity.persistInGameWorkerParamEntity(inGameWorkerParamEntity);

        userRepository.save(userEntity);

        log.info("Successfully persisted bought worker '{}' for user '{}'", fullName, userEntity.getLogin());
        return new TransactMoneyStatusResDto(0L, messageService.getMessage(AppLocaleSet.BOUGHT_WORKER_RES, Map.of(
            "workerName", fullName
        )));
    }
}
