package ru.practicum.ewm.event.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.dto.EventCreateDto;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.dto.UpdateEventUserRequest;
import ru.practicum.ewm.event.service.EventService;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor
public class PrivateEventController {

    private final EventService eventService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public EventFullDto create(@PathVariable Long userId, @RequestBody @Valid EventCreateDto eventDto) {
        log.info("Запрос пользователя {} на создание события {}", userId, eventDto);
        EventFullDto event = eventService.create(userId, eventDto);
        log.info("Создано событие: {}", event);
        return event;
    }

    @PatchMapping("/{eventId}")
    public EventFullDto update(@PathVariable Long userId, @PathVariable Long eventId, @RequestBody @Valid UpdateEventUserRequest eventDto) {
        log.info("Запрос пользователя {} на обновление события {} данными {}", userId, eventId, eventDto);
        EventFullDto event = eventService.updateEventByPrivate(userId, eventId, eventDto);
        log.info("Обновленное событие: {}", event);
        return event;
    }

    @GetMapping("/{eventId}")
    public EventFullDto getEventOfUser(@PathVariable Long userId, @PathVariable Long eventId) {
        log.info("Запрос события {} пользователя {}", eventId, userId);
        return eventService.getEventOfUser(userId, eventId);
    }

    @GetMapping
    public Collection<EventShortDto> findAllByPrivate(
            @PathVariable Long userId,
            @RequestParam(required = false, defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(required = false, defaultValue = "10") @Positive Integer size, HttpServletRequest request
    ) {
        log.info("Запрос событий пользователя {}" +
                        "\nfrom: {}" +
                        "\nsize: {}",
                userId, from, size);
        Collection<EventShortDto> events = eventService.findAllByPrivate(userId, from, size, request);
        log.info("Результат: {}", events);
        return events;
    }
}