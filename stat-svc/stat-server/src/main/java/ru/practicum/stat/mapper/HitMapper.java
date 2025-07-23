package ru.practicum.stat.mapper;

import org.mapstruct.Mapper;
import ru.practicum.stat.HitDto;
import ru.practicum.stat.model.Hit;

@Mapper(componentModel = "spring")
public interface HitMapper {

    HitDto hitToHitDto(Hit hit);

    Hit hitDtoToHit(HitDto hitDto);

}
