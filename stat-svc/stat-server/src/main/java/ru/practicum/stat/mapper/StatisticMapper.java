package ru.practicum.stat.mapper;

import org.mapstruct.Mapper;
import ru.practicum.stat.StatisticDto;
import ru.practicum.stat.model.Statistic;

@Mapper(componentModel = "spring")
public interface StatisticMapper {

    StatisticDto toStatisticDto(Statistic statistic);

    Statistic toStatistic(StatisticDto statisticDto);
}
