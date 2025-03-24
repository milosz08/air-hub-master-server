package pl.miloszgilga.ahms.exception.rest;

import lombok.extern.slf4j.Slf4j;
import org.jmpsl.core.exception.RestServiceServerException;
import org.springframework.http.HttpStatus;
import pl.miloszgilga.i18n.AppLocaleSet;

import java.util.List;

public class CrewException {
    @Slf4j
    public static class WorkerNotExistOrNotBoughtException extends RestServiceServerException {
        public WorkerNotExistOrNotBoughtException(List<Long> workerIds) {
            super(HttpStatus.NOT_FOUND, AppLocaleSet.WORKER_NOT_EXIST_OR_NOT_BOUGHT_EXC);
            log.error("Attemt to get wroker by id which does not exist or not bought. Followed ids: {}", workerIds);
        }
    }

    @Slf4j
    public static class PlaneNotExistOrNotBoughtException extends RestServiceServerException {
        public PlaneNotExistOrNotBoughtException(Long planeId) {
            super(HttpStatus.NOT_FOUND, AppLocaleSet.PLANE_NOT_EXIST_OR_NOT_BOUGHT_EXC);
            log.error("Attemt to get plane by id which does not exist or not bought. Followed id: {}", planeId);
        }
    }
}
