package ru.practicum.stat.mapper;

import org.mapstruct.Mapper;
import ru.practicum.stat.ViewStatsDto;
import ru.practicum.stat.model.ViewStats;

@Mapper(componentModel = "spring")
public interface ViewStatsMapper {

    ViewStatsDto toStatisticDto(ViewStats viewStats);

    ViewStats toStatistic(ViewStatsDto viewStatsDto);
}
