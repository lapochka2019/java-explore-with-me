package ru.practicum.stat.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.stat.HitCreateDto;
import ru.practicum.stat.HitDto;
import ru.practicum.stat.StatisticDto;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping
@Slf4j
@RequiredArgsConstructor
public class StatisticController {
    private final StatisticService statisticsService;

    @PostMapping("/hit")
    public HitDto create(@RequestBody HitCreateDto hit) {
        log.info("Запрос на создание нового Hit: {}", hit);
        return statisticsService.createHit(hit);
    }

    @GetMapping("/stats")
    public List<StatisticDto> getStats(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
            @RequestParam(required = false) List<String> uris,
            @RequestParam(defaultValue = "false") Boolean unique) {
        log.info("Запрос получения статистики");
        return statisticsService.getStats(start, end, uris, unique);
    }
}
