package ru.practicum.ewm.location.dto;

import org.mapstruct.Mapper;
import ru.practicum.ewm.location.model.Location;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    Location toLocation(LocationDto locationDto);

    LocationDto toLocationDto(Location location);
}
