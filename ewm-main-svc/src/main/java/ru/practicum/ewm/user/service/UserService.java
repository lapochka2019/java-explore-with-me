package ru.practicum.ewm.user.service;

import ru.practicum.ewm.user.dto.UserCreateDto;
import ru.practicum.ewm.user.model.User;

import java.util.List;

public interface UserService {

    User createUser(UserCreateDto userCreateDto);

    void deleteUser(Long id);

    List<User> getAllUsers(List<Long> ids, int from, int size);
}
