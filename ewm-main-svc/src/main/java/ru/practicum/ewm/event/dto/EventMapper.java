package ru.practicum.ewm.event.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.ewm.categories.dto.CategoryMapper;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.location.dto.LocationMapper;
import ru.practicum.ewm.user.dto.UserMapper;
import ru.practicum.ewm.utils.EventState;

@Mapper(componentModel = "spring", uses = {LocationMapper.class, UserMapper.class, CategoryMapper.class})
public interface EventMapper {

    EventShortDto toShortDto(Event event);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "initiator", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "publishedOn", ignore = true)
    @Mapping(target = "confirmedRequests", ignore = true)
    @Mapping(target = "views", ignore = true)
    @Mapping(target = "category", ignore = true)
    Event toEvent(EventCreateDto eventCreateDto, EventState state);

    EventFullDto toFullDto(Event event);
}
