package pl.miloszgilga.ahms.network.game.resdto;

import pl.miloszgilga.ahms.domain.in_game_plane_params.InGamePlaneParamEntity;
import pl.miloszgilga.ahms.network.game.dto.CrewInFlightDto;

import java.time.LocalDateTime;
import java.util.List;

public record InFlightPlaneResDto(
    String name,
    String categoryName,
    LocalDateTime arrival,
    List<CrewInFlightDto> workers
) {
    public InFlightPlaneResDto(InGamePlaneParamEntity plane, List<CrewInFlightDto> workers) {
        this(plane.getPlane().getName(), plane.getPlane().getCategory().getName(), plane.getAvailable(), workers);
    }
}
