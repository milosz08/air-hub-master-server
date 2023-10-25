/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.network.shop.resdto;

public record ShopWorkersResDto(
    Long id,
    String fullName,
    String categoryName,
    int experience,
    int cooperation,
    int rebelliousness,
    int skills,
    int price
) {
}
