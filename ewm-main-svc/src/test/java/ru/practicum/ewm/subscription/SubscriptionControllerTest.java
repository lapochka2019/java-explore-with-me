package ru.practicum.ewm.subscription;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.exception.ConflictException;
import ru.practicum.ewm.subscription.controller.SubscriptionController;
import ru.practicum.ewm.subscription.dto.SubscriberDto;
import ru.practicum.ewm.subscription.dto.SubscriptionDto;
import ru.practicum.ewm.subscription.dto.SubscriptionRequestDto;
import ru.practicum.ewm.subscription.service.SubscriptionService;
import ru.practicum.ewm.utils.FriendshipsStatus;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SubscriptionController.class)
@DisplayName("Тестирование SubscriptionController")
public class SubscriptionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SubscriptionService subscriptionService;

    @DisplayName("Успешная подписка на пользователя")
    @Test
    void subscribe_SuccessfulSubscription_ReturnsCreated() throws Exception {
        Long userId = 1L;
        SubscriptionRequestDto requestDto = new SubscriptionRequestDto();
        requestDto.setOwnerId(2L);

        SubscriptionDto responseDto = new SubscriptionDto();
        responseDto.setId(1L);
        responseDto.setFollowerId(userId);
        responseDto.setFriendshipsStatus(FriendshipsStatus.ONE_SIDED);

        when(subscriptionService.subscribe(userId, requestDto)).thenReturn(responseDto);

        mockMvc.perform(post("/users/{userId}/subscriptions", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.followerId").value(userId))
                .andExpect(jsonPath("$.friendshipsStatus").value(FriendshipsStatus.ONE_SIDED.name()));
    }

    @DisplayName("Попытка подписаться на самого себя")
    @Test
    void subscribe_SelfSubscription_ReturnsConflict() throws Exception {
        Long userId = 1L;
        SubscriptionRequestDto requestDto = new SubscriptionRequestDto();
        requestDto.setOwnerId(userId);

        when(subscriptionService.subscribe(userId, requestDto))
                .thenThrow(new ConflictException("Пользователь не может подписаться на самого себя"));

        mockMvc.perform(post("/users/{userId}/subscriptions", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isConflict());
    }

    @DisplayName("Успешная отписка от пользователя")
    @Test
    void unsubscribe_SuccessfulUnsubscription_ReturnsNoContent() throws Exception {
        Long userId = 1L;
        Long ownerId = 2L;

        doNothing().when(subscriptionService).unsubscribe(userId, ownerId);

        mockMvc.perform(delete("/users/{userId}/subscriptions/{ownerId}", userId, ownerId))
                .andExpect(status().isNoContent());
    }

    @DisplayName("Попытка отписаться от пользователя без подписки")
    @Test
    void unsubscribe_NoExistingSubscription_ReturnsConflict() throws Exception {
        Long userId = 1L;
        Long ownerId = 2L;

        doThrow(new ConflictException("У пользователя нет подписки на пользователя"))
                .when(subscriptionService).unsubscribe(userId, ownerId);

        mockMvc.perform(delete("/users/{userId}/subscriptions/{ownerId}", userId, ownerId))
                .andExpect(status().isConflict());
    }

    @DisplayName("Получение событий от подписок")
    @Test
    void getEventsFromSubscriptions_ReturnsPaginatedEvents() throws Exception {
        Long userId = 1L;
        EventShortDto eventDto = new EventShortDto();
        eventDto.setId(1L);
        eventDto.setTitle("Event Title");

        when(subscriptionService.getEventsFromSubscriptions(userId, 0, 10))
                .thenReturn(List.of(eventDto));

        mockMvc.perform(get("/users/{userId}/subscriptions/events", userId)
                        .param("from", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("Event Title"));
    }

    @DisplayName("Получение количества подписчиков")
    @Test
    void getSubscriberCount_ReturnsCorrectCount() throws Exception {
        Long userId = 1L;
        Long subscriberCount = 5L;

        when(subscriptionService.getSubscriberCount(userId)).thenReturn(subscriberCount);

        mockMvc.perform(get("/users/{userId}/subscriptions/subscribers/count", userId))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(subscriberCount)));
    }

    @DisplayName("Получение всех подписчиков")
    @Test
    void getAllSubscribers_ReturnsPaginatedSubscribers() throws Exception {
        Long userId = 1L;
        SubscriberDto subscriberDto = new SubscriberDto();
        subscriberDto.setUserId(2L);
        subscriberDto.setOwnerName("Owner Name");

        when(subscriptionService.getAllSubscribers(userId, 0, 10))
                .thenReturn(List.of(subscriberDto));

        mockMvc.perform(get("/users/{userId}/subscriptions/subscribers", userId)
                        .param("from", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value(2L))
                .andExpect(jsonPath("$[0].ownerName").value("Owner Name"));
    }
}