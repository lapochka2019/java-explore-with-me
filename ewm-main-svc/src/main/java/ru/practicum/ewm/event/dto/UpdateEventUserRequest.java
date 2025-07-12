package ru.practicum.ewm.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.ewm.location.model.Location;
import ru.practicum.ewm.utils.StateAction;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEventUserRequest {
    @Size(max = 2000, message = "Длина аннотации не должна превышать 2000 символов")
    @Size(min = 20, message = "Длина аннотации не должна быть не меньше 20")
    String annotation;
    Long category;

    @Size(max = 7000, message = "Длина описания не должна превышать 7000 символов")
    @Size(min = 20, message = "Длина описания не должна быть не меньше 20")
    String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime eventDate;
    Location location;
    Boolean paid;

    @Min(value = 0, message = "Лимит участников не может быть отрицательным")
    Integer participantLimit;
    Boolean requestModeration;
    StateAction stateAction;

    @Size(max = 120, message = "Длина аннотации не должна превышать 120 символов")
    @Size(min = 3, message = "Длина аннотации не должна быть не меньше 3")
    String title;
}