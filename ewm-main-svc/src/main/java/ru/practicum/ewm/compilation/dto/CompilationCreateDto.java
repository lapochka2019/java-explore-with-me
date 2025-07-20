package ru.practicum.ewm.compilation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompilationCreateDto {
    Long id;
    Set<Long> events;
    Boolean pinned;
    @NotBlank
    @Size(max = 50, message = "Длина заголовка не должна превышать 50 символов")
    String title;
}
