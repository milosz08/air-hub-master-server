package pl.miloszgilga.ahms.network.shop.resdto;

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
