package ru.practicum.stat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.stat.service.StatisticService;

import java.time.LocalDateTime;

@SpringBootTest(classes = StatisticApp.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DisplayName("Тестирование StatisticServiceImpl")
public class StatisticServiceImplTest {

    @Autowired
    StatisticService statisticService;

    @Test
    @DisplayName("Создать Hit")
    void createHit_withCorrectHit_returnCorrectHitDto() {
        HitCreateDto hitCreateDto = new HitCreateDto("app", "some usri", "123.123.0.0", LocalDateTime.now());
        HitDto hitDto = statisticService.createHit(hitCreateDto);

        Assertions.assertEquals(hitDto.getId(), 1L);
        Assertions.assertEquals(hitCreateDto.getIp(), hitDto.getIp());
        Assertions.assertEquals(hitCreateDto.getApp(), hitDto.getApp());
        Assertions.assertEquals(hitCreateDto.getUri(), hitDto.getUri());
        Assertions.assertEquals(hitCreateDto.getCreated(), hitDto.getCreated());
    }

}
