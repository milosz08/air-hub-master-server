package pl.miloszgilga.ahms.network.workshop.resdto;

import pl.miloszgilga.ahms.domain.in_game_plane_params.InGamePlaneParamEntity;

public record WorkshopPlaneStatsResDto(
    String name,
    String categoryName,
    int engine,
    int landingGeer,
    int wings
) {
    public WorkshopPlaneStatsResDto(InGamePlaneParamEntity plane) {
        this(plane.getPlane().getName(), plane.getPlane().getCategory().getName(), plane.getEngine(),
            plane.getLandingGeer(), plane.getWings());
    }
}
