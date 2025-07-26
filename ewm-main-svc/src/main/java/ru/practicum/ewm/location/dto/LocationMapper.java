package ru.practicum.ewm.location.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.ewm.location.model.Location;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    @Mapping(target = "longitude", source = "locationDto.lon")
    @Mapping(target = "latitude", source = "locationDto.lat")
    @Mapping(target = "id", ignore = true)
    Location toLocation(LocationDto locationDto);

    @Mapping(target = "lon", source = "location.longitude")
    @Mapping(target = "lat", source = "location.latitude")
    LocationDto toLocationDto(Location location);
}
