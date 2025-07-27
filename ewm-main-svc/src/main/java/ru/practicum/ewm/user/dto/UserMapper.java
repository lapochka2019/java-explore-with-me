package ru.practicum.ewm.user.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.ewm.user.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "allowSubscriptions", ignore = true)
    User toUser(UserCreateDto userCreateDto);

    UserShortDto toShortDto(User user);
}
