/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.network.category;

import pl.miloszgilga.network.category.resdto.CategoryResDto;
import pl.miloszgilga.network.category.resdto.CategoryWithTypeResDto;

import java.util.List;

interface CategoryService {
    List<CategoryWithTypeResDto> categories();
    List<CategoryResDto> categories(String type);
}
