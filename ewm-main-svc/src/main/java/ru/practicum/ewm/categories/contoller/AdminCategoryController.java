package ru.practicum.ewm.categories.contoller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.categories.dto.CategoryDto;
import ru.practicum.ewm.categories.model.Category;
import ru.practicum.ewm.categories.service.CategoryService;

@Slf4j
@RestController
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController {

    private final CategoryService service;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Category create(@RequestBody @Valid CategoryDto categoryDto) {
        log.info("Запрос на создание категории: {}", categoryDto);
        return service.create(categoryDto);
    }

    @PatchMapping("/{catId}")
    public Category update(@PathVariable Long catId, @RequestBody @Valid CategoryDto categoryDto) {
        log.info("Запрос на обновление категории c id: {}", catId);
        return service.update(catId, categoryDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{catId}")
    public void delete(@PathVariable Long catId) {
        log.info("Запрос на удаление категории с id: {}", catId);
        service.delete(catId);
    }
}