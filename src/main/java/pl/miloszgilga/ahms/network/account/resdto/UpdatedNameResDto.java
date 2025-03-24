package pl.miloszgilga.ahms.network.account.resdto;

import lombok.Builder;

@Builder
public record UpdatedNameResDto(
    String message,
    String newFirstName,
    String newLastName
) {
}
