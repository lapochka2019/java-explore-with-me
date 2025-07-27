package ru.practicum.ewm.subscription.service;

import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.subscription.dto.SubscriberDto;
import ru.practicum.ewm.subscription.dto.SubscriptionDto;
import ru.practicum.ewm.subscription.dto.SubscriptionRequestDto;

import java.util.List;

public interface SubscriptionService {
    SubscriptionDto subscribe(Long userId, SubscriptionRequestDto subscriptionRequestDto);

    void unsubscribe(Long userId, Long ownerId);

    List<EventShortDto> getEventsFromSubscriptions(Long userId, int from, int size);

    Long getSubscriberCount(Long userId);

    List<SubscriberDto> getAllSubscribers(Long userId, int from, int size);
}
