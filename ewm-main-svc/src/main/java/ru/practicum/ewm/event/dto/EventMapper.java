package ru.practicum.ewm.event.dto;

import org.mapstruct.Mapper;
import ru.practicum.ewm.event.model.Event;

@Mapper(componentModel = "spring")
public interface EventMapper {
    EventShortDto toShortDto(Event event);
}
