package pl.miloszgilga.ahms.network.account.resdto;

import lombok.Builder;

@Builder
public record UpdatedLoginResDto(
    String message,
    String newLogin,
    String updatedJwt
) {
}
