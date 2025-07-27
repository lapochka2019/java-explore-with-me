package ru.practicum.ewm.subscription.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.ewm.subscription.model.Subscription;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.utils.FriendshipsStatus;

import java.time.LocalDateTime;


@Mapper(componentModel = "spring")
public interface SubscriptionMapper {
    @Mapping(target = "followerId", source = "follower.id")
    SubscriptionDto toSubscriptionDto(Subscription subscription);

    @Mapping(target = "userId", source = "follower.id")
    @Mapping(target = "ownerName", source = "owner.name")
    SubscriberDto toSubscriberDto(Subscription subscription);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "follower", source = "follower")
    @Mapping(target = "owner", source = "owner")
    @Mapping(target = "subscribeTime", source = "subscribeTime")
    @Mapping(target = "unsubscribeTime", source = "unsubscribeTime")
    @Mapping(target = "friendshipsStatus", source = "friendshipsStatus")
    Subscription toSubscription(User follower, User owner,
                                LocalDateTime subscribeTime, LocalDateTime unsubscribeTime, FriendshipsStatus friendshipsStatus);
}
