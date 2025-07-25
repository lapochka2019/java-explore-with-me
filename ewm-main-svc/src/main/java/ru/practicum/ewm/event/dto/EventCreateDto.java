package ru.practicum.ewm.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.location.dto.LocationDto;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventCreateDto {
    @NotBlank
    @Size(min = 20, max = 2000, message = "Длина аннотации должна быть от 20 до 2000 символов")
    private String annotation;
    private Long category;

    @NotBlank
    @Size(min = 20, max = 7000, message = "Длина описания должна быть от 20 до 7000 символов")
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    private LocationDto location;
    private Boolean paid = false;

    @Min(value = 0, message = "Лимит участников не может быть отрицательным")
    private Integer participantLimit = 0;
    private Boolean requestModeration = true;

    @Size(min = 3, max = 120, message = "Длина аннотации должна быть от 3 до 120 символов")
    private String title;
}