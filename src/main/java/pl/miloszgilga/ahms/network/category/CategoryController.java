package pl.miloszgilga.ahms.network.category;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.miloszgilga.ahms.network.category.resdto.CategoryResDto;
import pl.miloszgilga.ahms.network.category.resdto.CategoryWithTypeResDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    ResponseEntity<List<CategoryWithTypeResDto>> categories() {
        return new ResponseEntity<>(categoryService.categories(), HttpStatus.OK);
    }

    @GetMapping("/filter/{type}")
    ResponseEntity<List<CategoryResDto>> categories(@PathVariable String type) {
        return new ResponseEntity<>(categoryService.categories(type), HttpStatus.OK);
    }
}
