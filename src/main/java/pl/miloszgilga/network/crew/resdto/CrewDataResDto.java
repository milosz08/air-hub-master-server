/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.network.crew.resdto;

import lombok.Builder;
import pl.miloszgilga.network.crew.dto.CrewPlaneDto;
import pl.miloszgilga.network.crew.dto.CrewWorkerDto;

import java.util.List;
import java.util.Map;

@Builder
public record CrewDataResDto(
    List<CrewPlaneDto> planes,
    Map<String, List<CrewWorkerDto>> workers
) {
}
