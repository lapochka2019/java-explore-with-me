package ru.practicum.stat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.stat.service.StatisticController;
import ru.practicum.stat.service.StatisticService;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StatisticController.class)
@DisplayName("Тестирование StatisticController")
public class StatisticControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StatisticService statisticsService;

    @Test
    void createHit_shouldReturnCreatedHitDto() throws Exception {
        LocalDateTime now = LocalDateTime.now();

        HitCreateDto requestDto = new HitCreateDto("app", "/uri", "192.168.0.1", now);
        HitDto responseDto = new HitDto(1L, "app", "/uri", "192.168.0.1", now);

        when(statisticsService.createHit(any(HitCreateDto.class))).thenReturn(responseDto);

        mockMvc.perform(post("/hit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                    {
                                      "app": "app",
                                      "uri": "/uri",
                                      "ip": "192.168.0.1",
                                      "timestamp": "%s"
                                    }
                                """.formatted(now)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.app").value("app"))
                .andExpect(jsonPath("$.uri").value("/uri"));
    }

    @Test
    void getStats_shouldReturnListOfStats() throws Exception {
        LocalDateTime start = LocalDateTime.of(2024, 1, 1, 10, 0);
        LocalDateTime end = LocalDateTime.of(2024, 1, 2, 10, 0);

        StatisticDto statDto = new StatisticDto("app", "/uri", 5L);
        List<StatisticDto> stats = List.of(statDto);

        when(statisticsService.getStats(eq(start), eq(end), isNull(), eq(false))).thenReturn(stats);

        mockMvc.perform(get("/stats")
                        .param("start", "2024-01-01 10:00:00")
                        .param("end", "2024-01-02 10:00:00")
                        .param("unique", "false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].hits").value(5));
    }
}
