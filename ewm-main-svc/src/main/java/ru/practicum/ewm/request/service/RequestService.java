package ru.practicum.ewm.request.service;

import ru.practicum.ewm.request.dto.RequestStatusUpdateRequest;
import ru.practicum.ewm.request.dto.RequestStatusUpdateResult;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;

import java.util.List;

public interface RequestService {

    ParticipationRequestDto create(Long userId, Long eventId);

    ParticipationRequestDto cancelRequest(Long userId, Long requestId);

    List<ParticipationRequestDto> getParticipationRequests(Long userId);

    List<ParticipationRequestDto> getParticipationRequestsForUserEvent(Long userId, Long eventId);

    RequestStatusUpdateResult updateStatus(Long userId, Long eventId,
                                           RequestStatusUpdateRequest dto);
}