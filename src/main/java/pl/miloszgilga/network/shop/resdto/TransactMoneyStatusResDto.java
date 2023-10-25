/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.network.shop.resdto;

public record TransactMoneyStatusResDto(
    Long moneyAfterTransact,
    String message
) {
}
