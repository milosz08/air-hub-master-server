/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: CategoryService.java
 * Last modified: 6/14/23, 12:12 AM
 * Project name: air-hub-master-server
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the License at
 *
 *     <http://www.apache.org/license/LICENSE-2.0>
 *
 * Unless required by applicable law or agreed to in writing, software distributed under
 * the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the license.
 */

package pl.miloszgilga.network.category;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

import pl.miloszgilga.network.category.resdto.CategoryResDto;
import pl.miloszgilga.network.category.resdto.CategoryWithTypeResDto;

import pl.miloszgilga.domain.category.CategoryType;
import pl.miloszgilga.domain.category.ICategoryRepository;

import static pl.miloszgilga.exception.CategoryException.CategoryTypeNotExistException;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService{

    private final ICategoryRepository categoryRepository;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public List<CategoryWithTypeResDto> categories() {
        return categoryRepository.findAll().stream()
            .map(c -> new CategoryWithTypeResDto(c.getName(), c.getType().name()))
            .toList();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
