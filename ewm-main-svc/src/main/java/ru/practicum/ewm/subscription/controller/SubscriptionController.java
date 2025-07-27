package ru.practicum.ewm.subscription.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.subscription.dto.SubscriberDto;
import ru.practicum.ewm.subscription.dto.SubscriptionDto;
import ru.practicum.ewm.subscription.dto.SubscriptionRequestDto;
import ru.practicum.ewm.subscription.service.SubscriptionService;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/subscriptions")
@RequiredArgsConstructor
@Slf4j
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SubscriptionDto subscribe(@PathVariable Long userId,
                                     @Valid @RequestBody SubscriptionRequestDto subscriptionRequestDto) {
        log.info("Запрос на подписку пользователя {} на пользователя {}", userId, subscriptionRequestDto.getOwnerId());
        return subscriptionService.subscribe(userId, subscriptionRequestDto);
    }

    @DeleteMapping("/{ownerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unsubscribe(@PathVariable Long userId, @PathVariable Long ownerId) {
        log.info("Запрос на отписку пользователя {} от пользователя {}", userId, ownerId);
        subscriptionService.unsubscribe(userId, ownerId);
    }

    @GetMapping("/events")
    public List<EventShortDto> getEventsFromSubscriptions(@PathVariable Long userId,
                                                          @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                                          @RequestParam(defaultValue = "10") @Positive int size) {
        log.info("Запрос событий от подписок пользователя {}", userId);
        return subscriptionService.getEventsFromSubscriptions(userId, from, size);
    }

    @GetMapping("/subscribers/count")
    public Long getSubscriberCount(@PathVariable Long userId) {
        log.info("Запрос количества подписчиков пользователя {}", userId);
        return subscriptionService.getSubscriberCount(userId);
    }

    @GetMapping("/subscribers")
    public List<SubscriberDto> getAllSubscribers(@PathVariable Long userId,
                                                 @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                                 @RequestParam(defaultValue = "10") @Positive int size) {
        log.info("Запрос всех подписчиков пользователя {}", userId);
        return subscriptionService.getAllSubscribers(userId, from, size);
    }
}