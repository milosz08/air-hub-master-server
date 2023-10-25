/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.network.shop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jmpsl.core.i18n.LocaleMessageService;
import org.jmpsl.security.user.AuthUser;
import org.springframework.stereotype.Service;
import pl.miloszgilga.algorithms.ComputedWorkerValues;
import pl.miloszgilga.algorithms.GameAlgorithms;
import pl.miloszgilga.domain.in_game_plane_params.InGamePlaneParamEntity;
import pl.miloszgilga.domain.in_game_worker_params.InGameWorkerParamEntity;
import pl.miloszgilga.domain.plane.PlaneEntity;
import pl.miloszgilga.domain.plane.PlaneRepository;
import pl.miloszgilga.domain.plane_route.PlaneRouteEntity;
import pl.miloszgilga.domain.user.UserEntity;
import pl.miloszgilga.domain.user.UserRepository;
import pl.miloszgilga.domain.worker.WorkerEntity;
import pl.miloszgilga.domain.worker.WorkerRepository;
import pl.miloszgilga.domain.workers_shop.WorkerShopEntity;
import pl.miloszgilga.domain.workers_shop.WorkerShopRepository;
import pl.miloszgilga.exception.GameException.PlaneNotExistException;
import pl.miloszgilga.exception.ShopException.AccountHasNotEnoughtMoneyException;
import pl.miloszgilga.exception.ShopException.WorkerInShopNotExistException;
import pl.miloszgilga.exception.ShopException.WorkerNotExistException;
import pl.miloszgilga.i18n.AppLocaleSet;
import pl.miloszgilga.network.shop.resdto.ShopPlanesResDto;
import pl.miloszgilga.network.shop.resdto.ShopWorkersResDto;
import pl.miloszgilga.network.shop.resdto.TransactMoneyStatusResDto;
import pl.miloszgilga.security.SecurityUtils;
import pl.miloszgilga.utils.Utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
@RequiredArgsConstructor
class ShopServiceImpl implements ShopService {
    private final SecurityUtils securityUtils;
    private final GameAlgorithms gameAlgorithms;
    private final LocaleMessageService messageService;

    private final UserRepository userRepository;
    private final PlaneRepository planeRepository;
    private final WorkerRepository workerRepository;
    private final WorkerShopRepository workerShopRepository;

    @Override
    public List<ShopPlanesResDto> planes(AuthUser user) {
        final UserEntity userEntity = securityUtils.getLoggedUser(user);
        final List<PlaneEntity> planeEntities = planeRepository.findAllLazillyLoadedPlanes();
        return planeEntities.stream()
            .filter(p -> userEntity.getLevel() >= p.getCategory().getLevel())
            .map(p -> new ShopPlanesResDto(p.getId(), p.getName(), p.getCategory().getName(), p.getPrice()))
            .toList();
    }

    @Override
    public List<ShopWorkersResDto> workers(AuthUser user) {
        final UserEntity userEntity = securityUtils.getLoggedUser(user);
        final List<WorkerEntity> workerEntities = workerRepository.findAllLazillyLoadedWorkers();

        final List<WorkerShopEntity> alreadyExisting = workerShopRepository.findByUseId(userEntity.getId());
        final List<ShopWorkersResDto> shopWorkersResDtos = new ArrayList<>();

        for (final WorkerEntity worker : workerEntities) {
            final AtomicInteger exp = new AtomicInteger();
            final AtomicInteger coop = new AtomicInteger();
            final AtomicInteger reb = new AtomicInteger();
            final AtomicInteger skills = new AtomicInteger();

            alreadyExisting.stream()
                .filter(s -> s.getWorker().getId().equals(worker.getId()))
                .findFirst()
                .ifPresentOrElse(workerShopEntity -> {
                    if (!userEntity.getWorkersBlocked()) {
                        final ComputedWorkerValues computed = gameAlgorithms.computeWorkerValues(userEntity.getLevel());
                        exp.set(computed.exp());
                        coop.set(computed.coop());
                        reb.set(computed.reb());
                        skills.set(computed.skills());
                        workerShopEntity.setAbilities(exp.get(), coop.get(), reb.get(), skills.get());
                    } else {
                        exp.set(workerShopEntity.getExperience());
                        coop.set(workerShopEntity.getCooperation());
                        reb.set(workerShopEntity.getRebelliousness());
                        skills.set(workerShopEntity.getSkills());
                    }
                }, () -> {
                    final ComputedWorkerValues computed = gameAlgorithms.computeWorkerValues(userEntity.getLevel());
                    exp.set(computed.exp());
                    coop.set(computed.coop());
                    reb.set(computed.reb());
                    skills.set(computed.skills());

                    final WorkerShopEntity workerShopEntity = new WorkerShopEntity(exp.get(), coop.get(),
                        reb.get(), skills.get());
                    workerShopEntity.setWorker(worker);
                    userEntity.setWorkersBlocked(true);
                    userEntity.persistWorkerShopEntity(workerShopEntity);
                });
            shopWorkersResDtos.add(new ShopWorkersResDto(worker.getId(), Utilities.parseWorkerFullName(worker),
                worker.getCategory().getName(), exp.get(), coop.get(), reb.get(), skills.get(), worker.getPrice()));
        }
        userRepository.save(userEntity);
        return shopWorkersResDtos;
    }

    @Override
    public TransactMoneyStatusResDto buyPlane(Long planeId, AuthUser user) {
        final UserEntity userEntity = securityUtils.getLoggedUser(user);

        final PlaneEntity boughtPlane = planeRepository.findById(planeId)
            .orElseThrow(() -> new PlaneNotExistException(planeId));

        if (userEntity.getMoney() - boughtPlane.getPrice() < 0) {
            throw new AccountHasNotEnoughtMoneyException(userEntity.getMoney(), boughtPlane.getPrice());
        }
        final InGamePlaneParamEntity inGamePlaneParamEntity = new InGamePlaneParamEntity();
        for (int i = 0; i < 3; i++) {
            final PlaneRouteEntity planeRouteEntity = new PlaneRouteEntity();
            inGamePlaneParamEntity.persistPlaneRouteEntity(planeRouteEntity);
        }
        inGamePlaneParamEntity.setPlane(boughtPlane);
        userEntity.persistInGamePlainParamEntity(inGamePlaneParamEntity);
        userEntity.setMoney(userEntity.getMoney() - boughtPlane.getPrice());

        userRepository.save(userEntity);

        log.info("Successfully persisted bought plane '{}' for user '{}'", boughtPlane.getName(), userEntity.getLogin());
        return new TransactMoneyStatusResDto(userEntity.getMoney(),
            messageService.getMessage(AppLocaleSet.BOUGHT_PLANE_RES, Map.of(
                "planeName", boughtPlane.getName()
            )));
    }

    @Override
    public TransactMoneyStatusResDto buyWorker(Long workerId, AuthUser user) {
        final UserEntity userEntity = securityUtils.getLoggedUser(user);

        final WorkerEntity boughtWorker = workerRepository.findById(workerId)
            .orElseThrow(() -> new WorkerNotExistException(workerId));

        if (userEntity.getMoney() - boughtWorker.getPrice() < 0) {
            throw new AccountHasNotEnoughtMoneyException(userEntity.getMoney(), boughtWorker.getPrice());
        }
        final Long uId = userEntity.getId();
        final String fullName = Utilities.parseWorkerFullName(boughtWorker);

        final WorkerShopEntity workerShopEntity = workerShopRepository.findByWorkerIdAndUserId(workerId, uId)
            .orElseThrow(() -> new WorkerInShopNotExistException(workerId, uId));

        final InGameWorkerParamEntity inGameWorkerParamEntity = new InGameWorkerParamEntity(workerShopEntity);

        inGameWorkerParamEntity.setWorker(boughtWorker);
        userEntity.persistInGameWorkerParamEntity(inGameWorkerParamEntity);
        userEntity.setMoney(userEntity.getMoney() - boughtWorker.getPrice());

        userRepository.save(userEntity);

        log.info("Successfully persisted bought worker '{}' for user '{}'", fullName, userEntity.getLogin());
        return new TransactMoneyStatusResDto(userEntity.getMoney(),
            messageService.getMessage(AppLocaleSet.BOUGHT_WORKER_RES, Map.of(
                "workerName", fullName
            )));
    }
}
