package pl.miloszgilga.ahms.network.crew.dto;

import pl.miloszgilga.ahms.domain.in_game_plane_params.InGamePlaneParamEntity;

public record CrewPlaneDto(
    Long id,
    String planeName,
    String categoryName
) {
    public CrewPlaneDto(InGamePlaneParamEntity plane) {
        this(plane.getId(), plane.getPlane().getName(), plane.getPlane().getCategory().getName());
    }
}
