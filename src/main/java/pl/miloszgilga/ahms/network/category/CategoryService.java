package pl.miloszgilga.ahms.network.category;

import pl.miloszgilga.ahms.network.category.resdto.CategoryResDto;
import pl.miloszgilga.ahms.network.category.resdto.CategoryWithTypeResDto;

import java.util.List;

interface CategoryService {
    List<CategoryWithTypeResDto> categories();

    List<CategoryResDto> categories(String type);
}
