package ru.practicum.ewm.location.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationDto {
    @NotNull(message = "Поле latitude не может быть пустым")
    private float latitude;
    @NotNull(message = "Поле longitude не может быть пустым")
    private float longitude;
}
