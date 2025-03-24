package pl.miloszgilga.ahms.network.game.resdto;

import lombok.Builder;
import pl.miloszgilga.ahms.network.game.dto.PlaneWithRoutesDto;

import java.util.List;

@Builder
public record PlanesWithRoutesResDto(
    List<String> planesCategories,
    List<PlaneWithRoutesDto> planes
) {
}
