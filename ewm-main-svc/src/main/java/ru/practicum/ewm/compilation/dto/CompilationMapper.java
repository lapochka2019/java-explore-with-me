package ru.practicum.ewm.compilation.dto;

import org.mapstruct.Mapper;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.model.Event;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface CompilationMapper {
    Compilation toCompilationWithEvents(CompilationCreateDto compilationCreateDto, Set<Event> events);

    CompilationDto toCompilationDto(Compilation compilation, List<EventShortDto> eventShortDtoList);
}
