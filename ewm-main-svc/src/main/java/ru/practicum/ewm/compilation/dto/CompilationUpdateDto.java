package ru.practicum.ewm.compilation.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompilationUpdateDto {
    Set<Long> events;
    Boolean pinned;

    @Size(max = 50, message = "Длина заголовка не должна превышать 50 символов")
    @Size(min = 1, message = "Заголовок должен содержать хотя бы 1 символ")
    String title;
}
