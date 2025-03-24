package pl.miloszgilga.ahms.network.auth;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.miloszgilga.ahms.domain.blacklist_jwt.BlacklistJwtEntity;
import pl.miloszgilga.ahms.domain.ota_token.OtaTokenEntity;
import pl.miloszgilga.ahms.domain.ota_token.OtaTokenRepository;
import pl.miloszgilga.ahms.domain.ota_token.OtaTokenType;
import pl.miloszgilga.ahms.domain.refresh_token.RefreshTokenEntity;
import pl.miloszgilga.ahms.domain.refresh_token.RefreshTokenRepository;
import pl.miloszgilga.ahms.domain.user.UserEntity;
import pl.miloszgilga.ahms.domain.user.UserRepository;
import pl.miloszgilga.ahms.dto.SimpleMessageResDto;
import pl.miloszgilga.ahms.exception.rest.AuthException;
import pl.miloszgilga.ahms.i18n.AppLocaleSet;
import pl.miloszgilga.ahms.i18n.LocaleMessageService;
import pl.miloszgilga.ahms.mail.MailRequestDto;
import pl.miloszgilga.ahms.mail.MailService;
import pl.miloszgilga.ahms.mail.MailTemplate;
import pl.miloszgilga.ahms.network.auth.reqdto.ActivateAccountReqDto;
import pl.miloszgilga.ahms.network.auth.reqdto.LoginReqDto;
import pl.miloszgilga.ahms.network.auth.reqdto.RefreshReqDto;
import pl.miloszgilga.ahms.network.auth.reqdto.RegisterReqDto;
import pl.miloszgilga.ahms.network.auth.resdto.JwtAuthenticationResDto;
import pl.miloszgilga.ahms.security.GrantedUserRole;
import pl.miloszgilga.ahms.security.LoggedUser;
import pl.miloszgilga.ahms.security.jwt.JwtClaim;
import pl.miloszgilga.ahms.security.jwt.JwtService;
import pl.miloszgilga.ahms.security.jwt.JwtValidPayload;
import pl.miloszgilga.ahms.security.jwt.JwtValidationType;
import pl.miloszgilga.ahms.security.ota.OtaProperties;
import pl.miloszgilga.ahms.security.ota.OtaService;
import pl.miloszgilga.ahms.utils.Utilities;

import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
class AuthServiceImpl implements AuthService {
    private final JwtService jwtService;
    private final OtaService otaService;
    private final OtaProperties otaProperties;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final LocaleMessageService localeMessageService;
    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;
    private final OtaTokenRepository otaTokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public JwtAuthenticationResDto login(LoginReqDto reqDto) {
        final var authenticationToken = new UsernamePasswordAuthenticationToken(reqDto.getLoginOrEmail(), reqDto.getPassword());
        final Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final var principal = (LoggedUser) authentication.getPrincipal();

        final UserEntity userEntity = userRepository.findUserByLoginOrEmail(principal.getUsername())
            .orElseThrow(AuthException.UserNotFoundException::new);

        final String token = jwtService.generateToken(userEntity);
        final RefreshTokenEntity refreshToken = refreshTokenRepository.findRefreshTokenByUserLogin(userEntity.getLogin())
            .orElseGet(() -> {
                final var generatedRefreshToken = jwtService.generateRefreshToken();
                final RefreshTokenEntity refreshTokenEntity = RefreshTokenEntity.builder()
                    .token(generatedRefreshToken.token())
                    .expiredAt(generatedRefreshToken.expiredDate())
                    .build();
                refreshTokenRepository.save(refreshTokenEntity);
                log.info("Successfully created refresh token for '{}' account", principal.getUsername());
                return refreshTokenEntity;
            });
        if (refreshToken.getExpiredAt().isBefore(ZonedDateTime.now())) {
            final var generatedRefreshToken = jwtService.generateRefreshToken();
            refreshToken.setToken(generatedRefreshToken.token());
            refreshToken.setExpiredAt(generatedRefreshToken.expiredDate());
            refreshTokenRepository.save(refreshToken);
            log.info("Successfully re-validated expired refresh token for '{}' account", principal.getUsername());
        }
        log.info("Successfully login on '{}' account", principal.getUsername());
        return JwtAuthenticationResDto.builder()
            .jwtToken(token)
            .refreshToken(refreshToken.getToken())
            .build();
    }

    @Override
    public SimpleMessageResDto register(RegisterReqDto reqDto) {
        final var generatedRefreshToken = jwtService.generateRefreshToken();
        final RefreshTokenEntity refreshTokenEntity = RefreshTokenEntity.builder()
            .token(generatedRefreshToken.token())
            .expiredAt(generatedRefreshToken.expiredDate())
            .build();

        String token;
        final Date expiredAt = DateUtils.addSeconds(Date.from(Instant.now()), otaProperties.getRegisterExpiredSec());
        do {
            token = otaService.generateToken();
        } while (otaTokenRepository.checkIfTokenAlreadyExist(token));

        final OtaTokenEntity otaTokenEntity = OtaTokenEntity.builder()
            .token(token)
            .type(OtaTokenType.ACTIVATE_ACCOUNT)
            .expiredAt(ZonedDateTime.ofInstant(expiredAt.toInstant(), ZonedDateTime.now().getZone()))
            .isUsed(false)
            .build();

        final UserEntity user = UserEntity.builder()
            .firstName(StringUtils.capitalize(reqDto.getFirstName()))
            .lastName(StringUtils.capitalize(reqDto.getLastName()))
            .login(reqDto.getLogin())
            .emailAddress(reqDto.getEmailAddress())
            .password(passwordEncoder.encode(reqDto.getPassword()))
            .role(GrantedUserRole.USER)
            .isActivated(false)
            .build();

        final Duration expiredAtDuration = Duration.ofSeconds(otaProperties.getRegisterExpiredSec());
        final MailRequestDto requestDto = mailService.createBaseMailRequest(user, AppLocaleSet.REGISTER_TITLE_MAIL);
        final Map<String, String> parameters = new HashMap<>();
        parameters.put("fullName", Utilities.parseFullName(user));
        parameters.put("token", token);
        parameters.put("expirationHours", String.valueOf(expiredAtDuration.get(ChronoUnit.DAYS)));

        mailService.sendEmail(requestDto, parameters, MailTemplate.REGISTER);

        user.persistRefreshToken(refreshTokenEntity);
        user.persistOtaToken(otaTokenEntity);
        userRepository.save(user);

        log.info("Successfully registered new user. User data: {}", user);
        return new SimpleMessageResDto(localeMessageService.getMessage(AppLocaleSet.REGISTERED_RES,
            Map.of("email", reqDto.getEmailAddress())));
    }

    @Override
    public SimpleMessageResDto activate(ActivateAccountReqDto reqDto) {
        final OtaTokenEntity token = otaTokenRepository.findTokenByType(reqDto.getToken(), OtaTokenType.ACTIVATE_ACCOUNT)
            .orElseThrow(() -> new AuthException.OtaTokenNotFoundException(reqDto.getToken()));

        final UserEntity user = token.getUser();
        if (user.getIsActivated()) {
            throw new AuthException.UserAccountHasBeenAlreadyActivatedException(user);
        }
        user.setIsActivated(true);
        token.setIsUsed(true);

        final MailRequestDto requestDto = mailService.createBaseMailRequest(user, AppLocaleSet.ACTIVATED_ACCOUNT_TITLE_MAIL);
        final Map<String, String> parameters = new HashMap<>();
        parameters.put("fullName", Utilities.parseFullName(user));

        mailService.sendEmail(requestDto, parameters, MailTemplate.ACTIVATED_ACCOUNT);
        otaTokenRepository.save(token);

        log.info("Account was successfully activated for user. User data: {}", token);
        return new SimpleMessageResDto(localeMessageService.getMessage(AppLocaleSet.ACTIVATED_ACCOUNT_RES));
    }

    @Override
    public JwtAuthenticationResDto refresh(RefreshReqDto reqDto) {
        final JwtValidPayload validationResult = jwtService.isValid(reqDto.getExpiredJwt());
        if (!validationResult.isValid() && !validationResult.checkType(JwtValidationType.EXPIRED)) {
            throw new AuthException.IncorrectJwtException(reqDto.getExpiredJwt());
        }
        final RefreshTokenEntity refreshToken = refreshTokenRepository.findRefreshTokenByToken(reqDto.getRefreshToken())
            .orElseThrow(() -> new AuthException.RefreshTokenNotFoundException(reqDto.getRefreshToken()));

        final Claims claims = jwtService.extractClaims(reqDto.getExpiredJwt())
            .orElseThrow(() -> new AuthException.IncorrectJwtException(reqDto.getExpiredJwt()));

        final String login = claims.get(JwtClaim.LOGIN.getName(), String.class);
        final UserEntity user = refreshToken.getUser();
        if (!login.equals(user.getLogin())) throw new AuthException.JwtIsNotRelatedWithRefrehTokenException(reqDto);

        log.info("JWT was successfully refreshed for user. User data: {}", user);
        return JwtAuthenticationResDto.builder()
            .jwtToken(jwtService.generateToken(user))
            .refreshToken(refreshToken.getToken())
            .build();
    }

    @Override
    public SimpleMessageResDto logout(HttpServletRequest req, LoggedUser loggedUser) {
        final String extractedToken = jwtService.extractToken(req);
        final UserEntity user = loggedUser.userEntity();

        final Claims claims = jwtService.extractClaims(extractedToken)
            .orElseThrow(() -> new AuthException.IncorrectJwtException(extractedToken));

        final BlacklistJwtEntity blacklistJwt = BlacklistJwtEntity.builder()
            .jwtToken(extractedToken)
            .expiredAt(ZonedDateTime.ofInstant(claims.getExpiration().toInstant(), ZonedDateTime.now().getZone()))
            .build();

        user.persistBlacklistJwt(blacklistJwt);
        userRepository.save(user);
        SecurityContextHolder.clearContext();

        log.info("Successfully logout from '{}' account", user.getLogin());
        return new SimpleMessageResDto(localeMessageService.getMessage(AppLocaleSet.LOGOUT_RES));
    }
}
