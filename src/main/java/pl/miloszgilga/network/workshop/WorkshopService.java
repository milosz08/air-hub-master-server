/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.network.workshop;

import org.jmpsl.security.user.AuthUser;
import pl.miloszgilga.network.workshop.resdto.WorkshopPlaneStatsResDto;

import java.util.List;

interface WorkshopService {
    List<WorkshopPlaneStatsResDto> getPlanes(AuthUser user);
}
