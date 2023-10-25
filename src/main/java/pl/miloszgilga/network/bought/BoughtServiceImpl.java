/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.network.bought;

import lombok.RequiredArgsConstructor;
import org.jmpsl.security.user.AuthUser;
import org.springframework.stereotype.Service;
import pl.miloszgilga.domain.in_game_plane_params.InGamePlaneParamRepository;
import pl.miloszgilga.domain.in_game_worker_params.InGameWorkerParamRepository;
import pl.miloszgilga.domain.user.UserEntity;
import pl.miloszgilga.network.bought.resdto.InGamePlaneResDto;
import pl.miloszgilga.network.bought.resdto.InGameWorkerResDto;
import pl.miloszgilga.security.SecurityUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
class BoughtServiceImpl implements BoughtService {
    private final SecurityUtils securityUtils;

    private final InGamePlaneParamRepository inGamePlaneParamRepository;
    private final InGameWorkerParamRepository inGameWorkerParamRepository;

    @Override
    public List<InGamePlaneResDto> boughtPlanes(AuthUser user) {
        final UserEntity userEntity = securityUtils.getLoggedUser(user);
        return inGamePlaneParamRepository.findAllByUser_Id(userEntity.getId()).stream()
            .map(InGamePlaneResDto::new)
            .toList();
    }

    @Override
    public List<InGameWorkerResDto> boughtWorkers(AuthUser user) {
        final UserEntity userEntity = securityUtils.getLoggedUser(user);
        return inGameWorkerParamRepository.findAllByUser_Id(userEntity.getId()).stream()
            .map(InGameWorkerResDto::new)
            .toList();
    }
}
