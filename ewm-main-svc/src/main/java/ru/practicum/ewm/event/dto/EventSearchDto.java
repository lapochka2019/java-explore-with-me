package ru.practicum.ewm.event.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import ru.practicum.ewm.utils.EventState;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventSearchDto {
    private String text;
    private List<Long> categories;
    private Boolean paid;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime rangeStart;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime rangeEnd;
    private Boolean onlyAvailable;
    private String sort;
    @PositiveOrZero
    private int from = 0;
    @Positive
    private int size = 10;
    private List<Long> users;
    private List<EventState> states;
}
