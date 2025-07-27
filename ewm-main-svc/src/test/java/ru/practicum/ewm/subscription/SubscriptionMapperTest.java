package ru.practicum.ewm.subscription;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.ewm.subscription.dto.SubscriberDto;
import ru.practicum.ewm.subscription.dto.SubscriptionDto;
import ru.practicum.ewm.subscription.dto.SubscriptionMapper;
import ru.practicum.ewm.subscription.dto.SubscriptionRequestDto;
import ru.practicum.ewm.subscription.model.Subscription;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.utils.FriendshipsStatus;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@DisplayName("Тестирование SubscriptionMapper")
public class SubscriptionMapperTest {

    @Autowired
    SubscriptionMapper subscriptionMapper;

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        user1 = new User(null, "Test User 1", "user1@example.com", true);

        user2 = new User(null, "Test User 2", "user2@example.com", false);
    }

    @DisplayName("Преобразовать корректный Subscription в SubscriptionDto")
    @Test
    void toSubscriptionDto_allSubscriptionFieldsFilled_returnCorrectSubscriptionDto() {
        Subscription subscription = new Subscription(2L, user1, user2, LocalDateTime.now(), null, FriendshipsStatus.NO_FRIENDSHIP);
        SubscriptionDto subscriptionDto = subscriptionMapper.toSubscriptionDto(subscription);
        assertEquals(subscriptionDto.getId(), subscription.getId());
        assertEquals(subscriptionDto.getFollowerId(), subscription.getFollower().getId());
        assertEquals(subscriptionDto.getOwner(), subscription.getOwner());
        assertEquals(subscriptionDto.getSubscribeTime(), subscription.getSubscribeTime());
        assertEquals(subscriptionDto.getUnsubscribeTime(), subscription.getUnsubscribeTime());
        assertEquals(subscriptionDto.getFriendshipsStatus(), subscription.getFriendshipsStatus());
    }

    @DisplayName("Преобразовать null в SubscriptionDto")
    @Test
    void toSubscriptionDto_withNullSubscription_returnNullSubscriptionDto() {
        SubscriptionDto subscriptionDto = subscriptionMapper.toSubscriptionDto(null);
        assertNull(subscriptionDto);
    }

    @DisplayName("Преобразовать корректный Subscription в SubscriberDto")
    @Test
    void toSubscriberDto_allSubscriptionFieldsFilled_returnCorrectSubscriberDto() {
        Subscription subscription = new Subscription(2L, user1, user2, LocalDateTime.now(), null, FriendshipsStatus.NO_FRIENDSHIP);
        SubscriberDto subscriberDto = subscriptionMapper.toSubscriberDto(subscription);
        assertEquals(subscriberDto.getUserId(), subscription.getFollower().getId());
        assertEquals(subscriberDto.getOwnerName(), subscription.getOwner().getName());
        assertEquals(subscriberDto.getSubscribeTime(), subscription.getSubscribeTime());
        assertEquals(subscriberDto.getFriendshipsStatus(), subscription.getFriendshipsStatus());
    }

    @DisplayName("Преобразовать null в SubscriberDto")
    @Test
    void toSubscriberDto_withNullSubscription_returnNullSubscriberDto() {
        SubscriberDto subscriberDto = subscriptionMapper.toSubscriberDto(null);
        assertNull(subscriberDto);
    }

    @DisplayName("Преобразовать корректный набор полей  в Subscription")
    @Test
    void toSubscription_allParameters_returnCorrectSubscription() {
        LocalDateTime time = LocalDateTime.now();
        SubscriptionRequestDto subscriptionRequestDto = new SubscriptionRequestDto(user2.getId());
        Subscription subscription = subscriptionMapper.toSubscription(user1, user2, time, null, FriendshipsStatus.NO_FRIENDSHIP);
        assertEquals(subscription.getFollower(), user1);
        assertEquals(subscription.getOwner(), user2);
        assertEquals(subscription.getSubscribeTime(), time);
        assertNull(subscription.getUnsubscribeTime());
        assertEquals(subscription.getFriendshipsStatus(), FriendshipsStatus.NO_FRIENDSHIP);
    }

    @DisplayName("Преобразовать null в Subscription")
    @Test
    void toSubscription_withNullParameters_returnNullSubscription() {
        Subscription subscription = subscriptionMapper.toSubscription(null, null, null, null, null);
        assertNull(subscription);
    }
}
