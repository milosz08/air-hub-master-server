package pl.miloszgilga.ahms.network.workshop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.miloszgilga.ahms.domain.in_game_plane_params.InGamePlaneParamRepository;
import pl.miloszgilga.ahms.domain.user.UserEntity;
import pl.miloszgilga.ahms.network.workshop.resdto.WorkshopPlaneStatsResDto;
import pl.miloszgilga.ahms.security.LoggedUser;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
class WorkshopServiceImpl implements WorkshopService {
    private final InGamePlaneParamRepository inGamePlaneParamRepository;

    @Override
    public List<WorkshopPlaneStatsResDto> getPlanes(LoggedUser loggedUser) {
        final UserEntity userEntity = loggedUser.userEntity();
        return inGamePlaneParamRepository.findAllByUser_IdAndAvailableIsNull(userEntity.getId()).stream()
            .map(WorkshopPlaneStatsResDto::new)
            .toList();
    }
}
