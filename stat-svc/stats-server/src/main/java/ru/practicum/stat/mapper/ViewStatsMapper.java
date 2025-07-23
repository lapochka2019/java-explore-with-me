package ru.practicum.stat.mapper;

import org.mapstruct.Mapper;
import ru.practicum.stat.ViewStatsDto;
import ru.practicum.stat.model.Statistic;

@Mapper(componentModel = "spring")
public interface ViewStatsMapper {

    ViewStatsDto toStatisticDto(Statistic statistic);

    Statistic toStatistic(ViewStatsDto viewStatsDto);
}
