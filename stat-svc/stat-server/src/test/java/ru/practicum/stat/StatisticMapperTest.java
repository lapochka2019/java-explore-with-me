package ru.practicum.stat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.stat.mapper.StatisticMapper;
import ru.practicum.stat.model.Statistic;

@SpringBootTest
@DisplayName("Тестирование StatisticMapper")
public class StatisticMapperTest {

    @Autowired
    StatisticMapper statisticMapper;

    @DisplayName("Преобразовать корректный Statistic в StatisticDto")
    @Test
    void toStatisticDto_allStatisticFieldsFilled_returnStatisticDto() {
        Statistic statistic = new Statistic("app", "some uri", 5L);
        StatisticDto statisticDto = statisticMapper.toStatisticDto(statistic);
        Assertions.assertEquals(statisticDto.getApp(), statistic.getApp());
        Assertions.assertEquals(statisticDto.getUri(), statistic.getUri());
        Assertions.assertEquals(statisticDto.getHits(), statistic.getHits());
    }

    @DisplayName("Преобразовать null в StatisticDto")
    @Test
    void toStatisticDto_withNullStatistic_returnNullStatisticDto() {
        StatisticDto statisticDto = statisticMapper.toStatisticDto(null);
        Assertions.assertEquals(statisticDto, null);

    }

    @DisplayName("Преобразовать корректный StatisticDto в Statistic")
    @Test
    void toStatisticDto_allStatisticDtoFieldsFilled_returnStatistic() {
        StatisticDto statisticDto = new StatisticDto("app", "some uri", 5L);
        Statistic statistic = statisticMapper.toStatistic(statisticDto);
        Assertions.assertEquals(statisticDto.getApp(), statistic.getApp());
        Assertions.assertEquals(statisticDto.getUri(), statistic.getUri());
        Assertions.assertEquals(statisticDto.getHits(), statistic.getHits());
    }

    @DisplayName("Преобразовать null в Statistic")
    @Test
    void toStatistic_withNullStatisticDto_returnNullStatistic() {
        Statistic statistic = statisticMapper.toStatistic(null);
        Assertions.assertEquals(statistic, null);

    }
}
