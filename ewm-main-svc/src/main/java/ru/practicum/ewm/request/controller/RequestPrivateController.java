package ru.practicum.ewm.request.controller;


import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;
import ru.practicum.ewm.request.dto.RequestStatusUpdateRequest;
import ru.practicum.ewm.request.dto.RequestStatusUpdateResult;
import ru.practicum.ewm.request.service.RequestService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users/{userId}")
@RequiredArgsConstructor
public class RequestPrivateController {

    private final RequestService eventRequestService;

    @PostMapping("/requests")
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto create(@PathVariable @Positive Long userId,
                                          @RequestParam @Positive Long eventId) {
        log.info("Запрос на создание заявки на участие пользователя {} в событии {}", userId, eventId);
        ParticipationRequestDto createdRequest = eventRequestService.create(userId, eventId);
        log.info("Создана заявка на участие: {}", createdRequest);
        return createdRequest;
    }

    @PatchMapping("/requests/{requestId}/cancel")
    public ParticipationRequestDto cancel(@PathVariable @Positive Long userId,
                                          @PathVariable @Positive Long requestId) {
        log.info("Отмена заявки {} на участие пользователя в событии {}", userId, requestId);
        ParticipationRequestDto cancelledRequest = eventRequestService.cancelRequest(userId, requestId);
        log.info("Заявка отменена: {}", cancelledRequest);
        return cancelledRequest;
    }

    @GetMapping("/requests")
    public List<ParticipationRequestDto> getParticipationRequests(@PathVariable @Positive Long userId) {
        log.info("Запрос на получение списка заявок на участие в событии пользователя {}", userId);
        List<ParticipationRequestDto> requests = eventRequestService.getParticipationRequests(userId);
        log.info("Получены заявки на участие: {}", requests);
        return requests;
    }

    @GetMapping("/events/{eventId}/requests")
    public List<ParticipationRequestDto> getParticipationRequestsForUserEvent(@PathVariable @Positive Long userId,
                                                                              @PathVariable @Positive Long eventId) {
        log.info("Запрос на получение списка заявок на участие в событии {} пользователя {}", userId, eventId);
        List<ParticipationRequestDto> requests = eventRequestService.getParticipationRequestsForUserEvent(userId, eventId);
        log.info("Получены заявки на участие в событии: {}", requests);
        return requests;
    }

    @PatchMapping("/events/{eventId}/requests")
    public RequestStatusUpdateResult updateStatus(@PathVariable @Positive Long userId,
                                                  @PathVariable @Positive Long eventId,
                                                  @RequestBody RequestStatusUpdateRequest requestDto) {
        log.info("Запрос на изменение статуса заявок события {} пользователя {} с телом {}", userId, eventId, requestDto);
        RequestStatusUpdateResult updateResult = eventRequestService.updateStatus(userId, eventId, requestDto);
        log.info("Обновлен статус заявок: {}", updateResult);
        return updateResult;
    }
}