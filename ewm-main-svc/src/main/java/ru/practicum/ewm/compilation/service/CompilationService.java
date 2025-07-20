package ru.practicum.ewm.compilation.service;

import ru.practicum.ewm.compilation.dto.CompilationCreateDto;
import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.model.Compilation;

import java.util.List;

public interface CompilationService {
    CompilationDto create(CompilationCreateDto newCompilationCreateDto);

    CompilationDto update(Long compId, CompilationCreateDto updateCompilationRequest);

    void delete(Long id);

    List<CompilationDto> getAllCompilations(Integer from, Integer size, Boolean pinned);

    CompilationDto findCompilationById(Long compId);
}

