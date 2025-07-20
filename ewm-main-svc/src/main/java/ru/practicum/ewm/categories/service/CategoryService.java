package ru.practicum.ewm.categories.service;

import ru.practicum.ewm.categories.dto.CategoryDto;
import ru.practicum.ewm.categories.model.Category;

import java.util.List;

public interface CategoryService {

    Category create(CategoryDto newCategoryDto);

    void delete(Long id);

    Category update(Long catId, CategoryDto updateCategoryDto);

    Category getCategoryById(Long catId);

    List<Category> getAllCategories(int from, int size);
}
