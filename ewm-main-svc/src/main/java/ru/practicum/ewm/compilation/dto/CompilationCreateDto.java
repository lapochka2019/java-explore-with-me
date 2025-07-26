package ru.practicum.ewm.compilation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.utils.Marker;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompilationCreateDto {
    Long id;
    Set<Long> events;
    Boolean pinned;

    @NotBlank (groups = {Marker.OnCreate.class})
    @Size(groups = {Marker.OnCreate.class, Marker.OnUpdate.class}, min = 1, max = 50, message = "Длина заголовка должна быть от 1 до 50 символов")
    String title;
}