/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.network.bought;

import org.jmpsl.security.user.AuthUser;
import pl.miloszgilga.network.bought.resdto.InGamePlaneResDto;
import pl.miloszgilga.network.bought.resdto.InGameWorkerResDto;

import java.util.List;

interface BoughtService {
    List<InGamePlaneResDto> boughtPlanes(AuthUser user);
    List<InGameWorkerResDto> boughtWorkers(AuthUser user);
}
