package ru.practicum.stat.service;

import ru.practicum.stat.HitCreateDto;
import ru.practicum.stat.HitDto;
import ru.practicum.stat.StatisticDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatisticService {
    HitDto createHit (HitCreateDto createDto);

    List<StatisticDto> getStats (LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);
}
