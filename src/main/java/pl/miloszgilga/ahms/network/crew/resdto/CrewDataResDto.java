package pl.miloszgilga.ahms.network.crew.resdto;

import lombok.Builder;
import pl.miloszgilga.ahms.network.crew.dto.CrewPlaneDto;
import pl.miloszgilga.ahms.network.crew.dto.CrewWorkerDto;

import java.util.List;
import java.util.Map;

@Builder
public record CrewDataResDto(
    List<CrewPlaneDto> planes,
    Map<String, List<CrewWorkerDto>> workers
) {
}
