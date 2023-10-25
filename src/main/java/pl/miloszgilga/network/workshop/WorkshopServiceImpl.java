/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.network.workshop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jmpsl.security.user.AuthUser;
import org.springframework.stereotype.Service;
import pl.miloszgilga.domain.in_game_plane_params.InGamePlaneParamRepository;
import pl.miloszgilga.domain.user.UserEntity;
import pl.miloszgilga.network.workshop.resdto.WorkshopPlaneStatsResDto;
import pl.miloszgilga.security.SecurityUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
class WorkshopServiceImpl implements WorkshopService {
    private final SecurityUtils securityUtils;
    private final InGamePlaneParamRepository inGamePlaneParamRepository;

    @Override
    public List<WorkshopPlaneStatsResDto> getPlanes(AuthUser user) {
        final UserEntity userEntity = securityUtils.getLoggedUser(user);
        return inGamePlaneParamRepository.findAllByUser_IdAndAvailableIsNull(userEntity.getId()).stream()
            .map(WorkshopPlaneStatsResDto::new)
            .toList();
    }
}
