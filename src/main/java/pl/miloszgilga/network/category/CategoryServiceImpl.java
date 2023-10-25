/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.network.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.miloszgilga.domain.category.CategoryRepository;
import pl.miloszgilga.domain.category.CategoryType;
import pl.miloszgilga.network.category.resdto.CategoryResDto;
import pl.miloszgilga.network.category.resdto.CategoryWithTypeResDto;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static pl.miloszgilga.exception.CategoryException.CategoryTypeNotExistException;

@Slf4j
@Service
@RequiredArgsConstructor
class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryWithTypeResDto> categories() {
        return categoryRepository.findAll().stream()
            .map(c -> new CategoryWithTypeResDto(c.getName(), c.getType().name()))
            .toList();
    }

    @Override
    public List<CategoryResDto> categories(String type) {
        return Arrays.stream(CategoryType.values())
            .filter(c -> c.name().equalsIgnoreCase(type))
            .findFirst()
            .map(categoryRepository::getAllByType)
            .orElseThrow(() -> new CategoryTypeNotExistException(type))
            .stream()
            .map(c -> new CategoryResDto(c.getName()))
            .collect(Collectors.toList());
    }
}
