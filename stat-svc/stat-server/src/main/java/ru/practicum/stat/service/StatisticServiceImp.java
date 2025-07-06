package ru.practicum.stat.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.stat.HitCreateDto;
import ru.practicum.stat.HitDto;
import ru.practicum.stat.StatisticDto;
import ru.practicum.stat.mapper.HitMapper;
import ru.practicum.stat.mapper.StatisticMapper;
import ru.practicum.stat.model.Hit;
import ru.practicum.stat.model.Statistic;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class StatisticServiceImp implements StatisticService {

    private final StatisticRepository statisticRepository;

    private final HitMapper hitMapper;

    private final StatisticMapper statisticMapper;

    @Override
    public HitDto createHit(HitCreateDto createDto) {
        Hit hit = hitMapper.createDtoToHit(createDto);
        log.info("Создан Hit с данными: {}", hit);
        Hit createdHit = statisticRepository.save(hit);
        return hitMapper.hitToHitDto(createdHit);
    }

    @Transactional(readOnly = true)
    @Override
    public List<StatisticDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        log.info("Получение статистики с start={}, end={}, uris={}, unique={}", start, end, uris, unique);
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Дата начала диапазона должна быть ДО даты конца диапазона");
        }
        List<Statistic> viewStats;

        if (unique) {
            if (uris != null && !uris.isEmpty()) {
                viewStats = statisticRepository.findStatsUniqueIp(start, end, uris);
            } else {
                viewStats = statisticRepository.findStatsUniqueIpAllUris(start, end);
            }
        } else {
            if (uris != null && !uris.isEmpty()) {
                viewStats = statisticRepository.findStats(start, end, uris);
            } else {
                viewStats = statisticRepository.findStatsAllUris(start, end);
            }
        }
        log.info("Получена статистика: {}", viewStats);

        return viewStats != null ? viewStats.stream()
                .map(statisticMapper::toStatisticDto)
                .collect(Collectors.toList()) : List.of();
    }
}
