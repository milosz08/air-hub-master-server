package pl.miloszgilga.ahms.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.miloszgilga.ahms.domain.blacklist_jwt.BlacklistJwtRepository;
import pl.miloszgilga.ahms.domain.in_game_plane_params.InGamePlaneParamRepository;
import pl.miloszgilga.ahms.domain.in_game_worker_params.InGameWorkerParamRepository;
import pl.miloszgilga.ahms.domain.ota_token.OtaTokenRepository;
import pl.miloszgilga.ahms.domain.user.UserRepository;
import pl.miloszgilga.ahms.security.ota.OtaProperties;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Slf4j
@Component
@EnableAsync
@RequiredArgsConstructor
public class AppScheduler {
    private final OtaProperties otaProperties;

    private final UserRepository userRepository;
    private final OtaTokenRepository otaTokenRepository;
    private final BlacklistJwtRepository blacklistJwtRepository;
    private final InGamePlaneParamRepository inGamePlaneParamRepository;
    private final InGameWorkerParamRepository inGameWorkerParamRepository;

    @Async
    @Transactional
    @Scheduled(cron = "0 */5 * * * *") // 5 minutes
    public void revertAvailablePlanesAndWorkersState() {
        inGamePlaneParamRepository.revertAvailablePlanesState(LocalDateTime.now());
        inGameWorkerParamRepository.revertAvailableWorkersState(LocalDateTime.now());
    }

    @Async
    @Transactional
    @Scheduled(cron = "0 0 */2 * * *") // 2 days
    public void removeUnactivatedUserAccounts() {
        final ZonedDateTime expiredAt = ZonedDateTime.now().plusSeconds(otaProperties.getRegisterExpiredSec());
        userRepository.deleteAllNotActivatedAccount(expiredAt);
        log.info("Invoke deleting unactivated account scheduler.");
    }

    @Async
    @Transactional
    @Scheduled(cron = "0 0 */1 * * *") // 1 day
    public void revalidateIsBlockedGeneratePlainsParam() {
        userRepository.revalidateAllBlockedWorkersAndRoutes();
        log.info("Revalidate isBlocked param for all users.");
    }

    @Async
    @Transactional
    @Scheduled(cron = "0 0 */1 * * *") // 1 hour
    public void removeExpiredBlacklistJwts() {
        blacklistJwtRepository.deleteAllExpiredJwts();
        log.info("Invoke deleting expierd JWTs on blacklist scheduler.");
    }

    @Async
    @Transactional
    @Scheduled(cron = "0 0 */1 * * *") // 1 day
    public void removeNonUserOtaTokens() {
        otaTokenRepository.deleteNonUsedOtaTokens();
        log.info("Invoke deleting non used ota tokens scheduler.");
    }
}
