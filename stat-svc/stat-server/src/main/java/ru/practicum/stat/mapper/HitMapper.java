package ru.practicum.stat.mapper;

import org.mapstruct.Mapper;
import ru.practicum.stat.HitCreateDto;
import ru.practicum.stat.HitDto;
import ru.practicum.stat.model.Hit;

@Mapper(componentModel = "spring")
public interface HitMapper {

    Hit createDtoToHit (HitCreateDto createDto);

    HitDto hitToHitDto (Hit hit);

}
