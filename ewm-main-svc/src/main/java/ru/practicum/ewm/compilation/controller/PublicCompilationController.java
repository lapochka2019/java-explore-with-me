package ru.practicum.ewm.compilation.controller;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.service.CompilationService;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/compilations")
@RequiredArgsConstructor
public class PublicCompilationController {
    private final CompilationService compilationService;

    @GetMapping
    public List<CompilationDto> getAllCompilations(
            @RequestParam(required = false) Boolean pinned,
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(defaultValue = "10") @Positive Integer size) {
        log.info("Запрос на получение подборок событий с параметрами:" +
                "\npinned={}" +
                "\nfrom={}" +
                "\nsize={}", pinned, from, size);
        List<CompilationDto> compilations = compilationService.getAllCompilations(from, size, pinned);
        log.info("Получен список подборок событий: {}", compilations);
        return compilations;
    }

    @GetMapping("/{compId}")
    public CompilationDto findCompilationById(@PathVariable Long compId) {
        log.info("Запрос на получение подборки событий по id {}", compId);
        CompilationDto compilation = compilationService.findCompilationById(compId);
        log.info("Получена подборка событий по id {}: {}", compId, compilation);
        return compilation;
    }
}
