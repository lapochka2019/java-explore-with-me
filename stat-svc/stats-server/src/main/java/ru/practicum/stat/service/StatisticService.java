package ru.practicum.stat.service;

import ru.practicum.stat.HitDto;
import ru.practicum.stat.ViewStatsDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatisticService {
    HitDto createHit(HitDto createDto);

    List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);
}
