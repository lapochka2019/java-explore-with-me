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
    @Size(max = 2000, message = "Длина аннотации не должна превышать 2000 символов")
    @Size(min = 20, message = "Длина аннотации не должна быть не меньше 20")
    private String annotation;
    private Long category;

    @NotBlank
    @Size(max = 7000, message = "Длина описания не должна превышать 7000 символов")
    @Size(min = 20, message = "Длина описания не должна быть не меньше 20")
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    private LocationDto location;
    private Boolean paid = false;

    @Min(value = 0, message = "Лимит участников не может быть отрицательным")
    private Integer participantLimit = 0;
    private Boolean requestModeration = true;

    @Size(max = 120, message = "Длина аннотации не должна превышать 120 символов")
    @Size(min = 3, message = "Длина аннотации не должна быть не меньше 3")
    private String title;
}