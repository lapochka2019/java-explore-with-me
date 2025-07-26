package ru.practicum.ewm.categories.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    @NotBlank(message = "Название категории должно быть указано!")
    @Size(max = 50, message = "Длина name не должна превышать 50 символов!")
    private String name;
}
