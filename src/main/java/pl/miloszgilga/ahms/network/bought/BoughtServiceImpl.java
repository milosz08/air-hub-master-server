package pl.miloszgilga.ahms.network.bought;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.miloszgilga.ahms.domain.in_game_plane_params.InGamePlaneParamRepository;
import pl.miloszgilga.ahms.domain.in_game_worker_params.InGameWorkerParamRepository;
import pl.miloszgilga.ahms.domain.user.UserEntity;
import pl.miloszgilga.ahms.network.bought.resdto.InGamePlaneResDto;
import pl.miloszgilga.ahms.network.bought.resdto.InGameWorkerResDto;
import pl.miloszgilga.ahms.security.LoggedUser;

import java.util.List;

@Service
@RequiredArgsConstructor
class BoughtServiceImpl implements BoughtService {
    private final InGamePlaneParamRepository inGamePlaneParamRepository;
    private final InGameWorkerParamRepository inGameWorkerParamRepository;

    @Override
    public List<InGamePlaneResDto> boughtPlanes(LoggedUser loggedUser) {
        final UserEntity userEntity = loggedUser.userEntity();
        return inGamePlaneParamRepository.findAllByUser_Id(userEntity.getId()).stream()
            .map(InGamePlaneResDto::new)
            .toList();
    }

    @Override
    public List<InGameWorkerResDto> boughtWorkers(LoggedUser loggedUser) {
        final UserEntity userEntity = loggedUser.userEntity();
        return inGameWorkerParamRepository.findAllByUser_Id(userEntity.getId()).stream()
            .map(InGameWorkerResDto::new)
            .toList();
    }
}
