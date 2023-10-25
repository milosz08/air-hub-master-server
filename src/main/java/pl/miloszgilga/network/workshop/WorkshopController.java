/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.network.workshop;

import lombok.RequiredArgsConstructor;
import org.jmpsl.security.user.AuthUser;
import org.jmpsl.security.user.CurrentUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.miloszgilga.network.workshop.resdto.WorkshopPlaneStatsResDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/workshop")
class WorkshopController {
    private final WorkshopService workshopService;

    @GetMapping("/planes")
    ResponseEntity<List<WorkshopPlaneStatsResDto>> getPlanes(@CurrentUser AuthUser user) {
        return new ResponseEntity<>(workshopService.getPlanes(user), HttpStatus.OK);
    }
}
