package ru.practicum.ewm.subscription;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.ewm.MainApp;
import ru.practicum.ewm.event.dto.EventMapper;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exception.ConflictException;
import ru.practicum.ewm.subscription.dto.SubscriptionDto;
import ru.practicum.ewm.subscription.dto.SubscriptionMapper;
import ru.practicum.ewm.subscription.dto.SubscriptionRequestDto;
import ru.practicum.ewm.subscription.model.Subscription;
import ru.practicum.ewm.subscription.repository.SubscriptionRepository;
import ru.practicum.ewm.subscription.service.SubscriptionService;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.repository.UserRepository;
import ru.practicum.ewm.utils.FriendshipsStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = MainApp.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DisplayName("Тестирование SubscriptionServiceImpl")
public class SubscriptionServiceImplTest {

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private SubscriptionMapper subscriptionMapper;

    @Autowired
    private EventMapper eventMapper;

    @DisplayName("Проверка успешной подписки на пользователя")
    @Test
    void subscribe_SuccessfulSubscription_ReturnsSubscriptionDto() {
        User follower = new User();
        follower.setName("Follower");
        follower.setEmail("follower@example.com");
        follower.setAllowSubscriptions(true);
        userRepository.save(follower);

        User owner = new User();
        owner.setName("Owner");
        owner.setEmail("owner@example.com");
        owner.setAllowSubscriptions(true);
        userRepository.save(owner);

        SubscriptionRequestDto request = new SubscriptionRequestDto();
        request.setOwnerId(owner.getId());

        SubscriptionDto result = subscriptionService.subscribe(follower.getId(), request);

        assertNotNull(result);
        assertEquals(follower.getId(), result.getFollowerId());
        assertEquals(owner.getId(), result.getOwner().getId());
        assertEquals(FriendshipsStatus.ONE_SIDED, result.getFriendshipsStatus());
    }

    @DisplayName("Проверка невозможности подписаться на самого себя")
    @Test
    void subscribe_SelfSubscription_ThrowsConflictException() {
        User user = new User();
        user.setName("User");
        user.setEmail("user@example.com");
        user.setAllowSubscriptions(true);
        userRepository.save(user);

        SubscriptionRequestDto request = new SubscriptionRequestDto();
        request.setOwnerId(user.getId());

        ConflictException exception = assertThrows(
                ConflictException.class,
                () -> subscriptionService.subscribe(user.getId(), request),
                "Ожидается, что будет выброшено исключение ConflictException"
        );

        assertTrue(exception.getMessage().contains("Пользователь не может подписаться на самого себя"));
    }

    @DisplayName("Проверка успешной отписки от пользователя")
    @Test
    void unsubscribe_SuccessfulUnsubscription_DeletesSubscription() {
        User follower = new User();
        follower.setName("Follower");
        follower.setEmail("follower@example.com");
        follower.setAllowSubscriptions(true);
        userRepository.save(follower);

        User owner = new User();
        owner.setName("Owner");
        owner.setEmail("owner@example.com");
        owner.setAllowSubscriptions(true);
        userRepository.save(owner);

        Subscription subscription = new Subscription();
        subscription.setFollower(follower);
        subscription.setOwner(owner);
        subscription.setSubscribeTime(LocalDateTime.now());
        subscription.setFriendshipsStatus(FriendshipsStatus.ONE_SIDED);
        subscriptionRepository.save(subscription);

        subscriptionService.unsubscribe(follower.getId(), owner.getId());

        Optional<Subscription> deletedSubscription = subscriptionRepository.findByFollowerAndOwner(follower, owner);
        assertTrue(deletedSubscription.isEmpty());
    }

    @DisplayName("Проверка отписки от пользователя без существующей подписки")
    @Test
    void unsubscribe_NoExistingSubscription_ThrowsConflictException() {
        User follower = new User();
        follower.setName("Follower");
        follower.setEmail("follower@example.com");
        follower.setAllowSubscriptions(true);
        userRepository.save(follower);

        User owner = new User();
        owner.setName("Owner");
        owner.setEmail("owner@example.com");
        owner.setAllowSubscriptions(true);
        userRepository.save(owner);

        ConflictException exception = assertThrows(
                ConflictException.class,
                () -> subscriptionService.unsubscribe(follower.getId(), owner.getId()),
                "Ожидается, что будет выброшено исключение ConflictException"
        );

        assertTrue(exception.getMessage().contains("У пользователя нет подписки на пользователя"));
    }

    @DisplayName("Проверка получения событий от подписок")
    @Test
    void getEventsFromSubscriptions_ReturnsPaginatedEvents() {
        User follower = new User();
        follower.setName("Follower");
        follower.setEmail("follower@example.com");
        follower.setAllowSubscriptions(true);
        userRepository.save(follower);

        User owner = new User();
        owner.setName("Owner");
        owner.setEmail("owner@example.com");
        owner.setAllowSubscriptions(true);
        userRepository.save(owner);

        Subscription subscription = new Subscription();
        subscription.setFollower(follower);
        subscription.setOwner(owner);
        subscription.setSubscribeTime(LocalDateTime.now());
        subscription.setFriendshipsStatus(FriendshipsStatus.ONE_SIDED);
        subscriptionRepository.save(subscription);

        Event event = new Event();
        event.setInitiator(owner);
        event.setEventDate(LocalDateTime.now());
        eventRepository.save(event);

        List<EventShortDto> events = subscriptionService.getEventsFromSubscriptions(follower.getId(), 0, 10);

        assertFalse(events.isEmpty());
        assertEquals(1, events.size());
        assertEquals(eventMapper.toShortDto(event).getTitle(), events.get(0).getTitle());
    }

    @DisplayName("Проверка подсчета подписчиков")
    @Test
    void getSubscriberCount_ReturnsCorrectCount() {
        User owner = new User();
        owner.setName("Owner");
        owner.setEmail("owner@example.com");
        owner.setAllowSubscriptions(true);
        userRepository.save(owner);

        User follower1 = new User();
        follower1.setName("Follower1");
        follower1.setEmail("follower1@example.com");
        follower1.setAllowSubscriptions(true);
        userRepository.save(follower1);

        User follower2 = new User();
        follower2.setName("Follower2");
        follower2.setEmail("follower2@example.com");
        follower2.setAllowSubscriptions(true);
        userRepository.save(follower2);

        Subscription subscription1 = new Subscription();
        subscription1.setFollower(follower1);
        subscription1.setOwner(owner);
        subscription1.setSubscribeTime(LocalDateTime.now());
        subscription1.setFriendshipsStatus(FriendshipsStatus.ONE_SIDED);
        subscriptionRepository.save(subscription1);

        Subscription subscription2 = new Subscription();
        subscription2.setFollower(follower2);
        subscription2.setOwner(owner);
        subscription2.setSubscribeTime(LocalDateTime.now());
        subscription2.setFriendshipsStatus(FriendshipsStatus.MUTUAL);
        subscriptionRepository.save(subscription2);

        Long count = subscriptionService.getSubscriberCount(owner.getId());

        assertEquals(2, count);
    }
}