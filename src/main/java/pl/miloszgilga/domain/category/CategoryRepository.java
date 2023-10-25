/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.domain.category;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    @Cacheable(cacheNames = "CategoryEntity.getAll.Cache")
    List<CategoryEntity> findAll();

    @Cacheable(cacheNames = "CategoryEntity.getAllByType.Cache")
    List<CategoryEntity> getAllByType(CategoryType type);
}
