package pl.miloszgilga.ahms.network.shop.resdto;

public record TransactMoneyStatusResDto(
    Long moneyAfterTransact,
    String message
) {
}
