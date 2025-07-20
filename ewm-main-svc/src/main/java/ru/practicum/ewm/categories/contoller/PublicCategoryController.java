package ru.practicum.ewm.categories.contoller;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.categories.model.Category;
import ru.practicum.ewm.categories.service.CategoryService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class PublicCategoryController {

    private final CategoryService service;

    @GetMapping("/{catId}")
    public Category getCategoryById(@PathVariable Long catId) {
        log.info("Запрос на получение категории c id: {}", catId);
        return service.getCategoryById(catId);
    }

    @GetMapping
    public List<Category> getAllCategories(@RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                           @RequestParam(defaultValue = "10") @Positive int size) {
        log.info("Запрос на получение списка всех категорий с параметрами:" +
                        "\n from: {}" +
                        "\n size: {}",
                from, size);
        return service.getAllCategories(from, size);
    }
}
