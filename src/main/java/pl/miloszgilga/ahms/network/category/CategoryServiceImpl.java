package pl.miloszgilga.ahms.network.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.miloszgilga.ahms.domain.category.CategoryRepository;
import pl.miloszgilga.ahms.domain.category.CategoryType;
import pl.miloszgilga.ahms.exception.rest.CategoryException;
import pl.miloszgilga.ahms.network.category.resdto.CategoryResDto;
import pl.miloszgilga.ahms.network.category.resdto.CategoryWithTypeResDto;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
            .orElseThrow(() -> new CategoryException.CategoryTypeNotExistException(type))
            .stream()
            .map(c -> new CategoryResDto(c.getName()))
            .collect(Collectors.toList());
    }
}
