/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.exception;

import lombok.extern.slf4j.Slf4j;
import org.jmpsl.core.exception.RestServiceServerException;
import org.springframework.http.HttpStatus;
import pl.miloszgilga.domain.in_game_plane_params.InGamePlaneParamEntity;
import pl.miloszgilga.i18n.AppLocaleSet;

public class GameException {
    @Slf4j
    public static class PlaneNotExistException extends RestServiceServerException {
        public PlaneNotExistException(Long nonExistingPlaneId) {
            super(HttpStatus.NOT_FOUND, AppLocaleSet.PLANE_NOT_EXIST_EXC);
            log.error("Attemt to get plane by id which does not exist. Followed id: {}", nonExistingPlaneId);
        }
    }

    @Slf4j
    public static class NotEnoughtRoutesException extends RestServiceServerException {
        public NotEnoughtRoutesException(InGamePlaneParamEntity plane) {
            super(HttpStatus.NOT_FOUND, AppLocaleSet.NOT_ENOUGHT_ROUTES_EXC);
            log.error("Attemt to get plane with not generated 3 routes. In game plane: {}", plane);
        }
    }

    @Slf4j
    public static class RouteNotFoundException extends RestServiceServerException {
        public RouteNotFoundException(Long routeId) {
            super(HttpStatus.NOT_FOUND, AppLocaleSet.NOT_ENOUGHT_ROUTES_EXC);
            log.error("Attemt to get route with followed id or is not connected with plane. Route id: {}", routeId);
        }
    }

    @Slf4j
    public static class LockedPlaneException extends RestServiceServerException {
        public LockedPlaneException(Long planeId) {
            super(HttpStatus.NOT_FOUND, AppLocaleSet.LOCKED_PLANE_EXC);
            log.error("Attemt to get plane, when is current locked. Plane id: {}", planeId);
        }
    }

    @Slf4j
    public static class NonExistingTempStatsException extends RestServiceServerException {
        public NonExistingTempStatsException(Long planeId) {
            super(HttpStatus.NOT_FOUND, AppLocaleSet.NON_EXISTING_TEMP_STATS_EXC);
            log.error("Attemt to get plane, when is current locked. Plane id: {}", planeId);
        }
    }
}
