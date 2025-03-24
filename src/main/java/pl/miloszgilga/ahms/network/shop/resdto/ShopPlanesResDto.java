package pl.miloszgilga.ahms.network.shop.resdto;

public record ShopPlanesResDto(
    Long id,
    String planeName,
    String categoryName,
    int landingGeer,
    int wings,
    int engine,
    byte upgrade,
    int price
) {
    public ShopPlanesResDto(Long id, String planeName, String categoryName, int price) {
        this(id, planeName, categoryName, 100, 100, 100, (byte) 0, price);
    }
}
