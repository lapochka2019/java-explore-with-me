package ru.practicum.ewm.user.dto;

import org.mapstruct.Mapper;
import ru.practicum.ewm.user.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser (UserCreateDto userCreateDto);

    UserShortDto toShortDto (User user);
}
